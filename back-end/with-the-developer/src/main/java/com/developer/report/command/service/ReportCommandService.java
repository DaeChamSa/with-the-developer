package com.developer.report.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.comu.post.command.repository.ComuPostRepository;
import com.developer.project.post.command.domain.repository.ProjPostRepository;
import com.developer.recruit.command.entity.ApprStatus;
import com.developer.recruit.command.repository.RecruitRepository;
import com.developer.report.command.dto.ReportCreateDTO;
import com.developer.report.command.dto.ReportCreateResultDTO;
import com.developer.report.command.entity.Report;
import com.developer.report.command.entity.ReportReasonCategory;
import com.developer.report.command.entity.ReportType;
import com.developer.report.command.repository.ReportReasonCategoryRepository;
import com.developer.report.command.repository.ReportRepository;

import com.developer.team.post.command.repository.TeamPostRepository;
import com.developer.user.command.domain.aggregate.BannedUser;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.aggregate.UserStatus;
import com.developer.user.command.domain.repository.BannedUserRepository;
import com.developer.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportCommandService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ComuPostRepository comuPostRepository;
    private final RecruitRepository recruitRepository;
    private final ProjPostRepository projPostRepository;
    private final TeamPostRepository teamPostRepository;
    private final ReportReasonCategoryRepository reportReasonCategoryRepository;
    private final BannedUserRepository bannedUserRepository;

    // 회원 정지 기준
    private static final int USER_BAN_THRESHOLD = 10;
    // 회원 강제 탈퇴 기준
    private static final int USER_DELETE_THRESHOLD = 20;
    // 게시물 block 기준
    public static final int POST_BLOCK_THRESHOLD = 5;

    @Transactional
    public Long createAndHandleReport(ReportCreateDTO reportCreateDTO, Long userCode, Long postCode, ReportType reportType) {
        ReportHandler reportHandler = getReportHandler(reportType);

        // 신고 생성하기
        reportHandler.checkStatus(postCode);
        ReportCreateResultDTO reportCreateResult = createReport(reportCreateDTO, userCode, postCode, reportHandler);
        Report report = reportCreateResult.getReport();
        User reportedUser = reportCreateResult.getReportedUser();

        // 신고가 새로 생성되었으니 저장
        reportRepository.save(report);
        userRepository.save(reportedUser);

        int reportedCount = reportHandler.getReportedCount(report);

        if (reportedCount >= POST_BLOCK_THRESHOLD) {
            // 신고가 들어온 게시물 횟수까지 합쳐서 해당 게시물 신고 횟수가 게시물 block 기준을 넘으면 해당 게시물 block
            reportHandler.deletePost(report);
            // 자동으로 신고 처리가 되었을 때, 신고에 대한 승인상태와 날짜 업데이트
            updateReportApprovalAndResolveDateAuto(report, reportHandler);
        }

        // 신고가 들어온 게시물 작성자의 신고 횟수에 따른 처리
        handleReportedUser(reportedUser);

        return report.getRepoCode();
    }

    // 게시물의 repoStatus를 APPROVE로 변경하고 그 시간을 저장하는 메서드
    public void updateReportApprovalAndResolveDateAuto(Report report, ReportHandler reportHandler) {
        List<Report> listToBeApproved = reportHandler.getListToBeApproved(report);
        for (Report toBeApproved : listToBeApproved) {
            toBeApproved.updateRepoStatus(ApprStatus.APPROVE);
            toBeApproved.updateReportResolveDate();
        }
    }

    public ReportCreateResultDTO createReport(ReportCreateDTO reportCreateDTO, Long userCode, Long postCode, ReportHandler reportHandler) {
        // 신고자
        User reportingUser = userRepository.findById(userCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // 신고 사유 카테고리
        ReportReasonCategory reportReasonCategory = reportReasonCategoryRepository.findByRepoReasonName(reportCreateDTO.getReportReasonCategory())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REPORT_REASON_CATEGORY));

        // 신고 엔티티 생성 및 설정
        Report report = reportCreateDTO.toEntity();
        report.updateUser(reportingUser);
        report.updateRepoReasonCategory(reportReasonCategory);

        User reportedUser = reportHandler.findReportedUser(report, postCode);
        return new ReportCreateResultDTO(report, reportedUser);
    }

    private ReportHandler getReportHandler(ReportType reportType) {
        switch (reportType) {
            case COMU:
                return new ComuReportHandler(comuPostRepository, reportRepository);
            case RECRUIT:
                return new RecruitReportHandler(recruitRepository, reportRepository);
            case TEAMPOST:
                return new TeamPostReportHandler(teamPostRepository, reportRepository);
            case PROJPOST:
                return new ProjPostReportHandler(projPostRepository, reportRepository, userRepository);
            default:
                throw new CustomException(ErrorCode.NO_VALID_VALUE);
        }
    }

    // 신고된 게시물의 게시물 작성자의 지금까지 받은 신고 횟수가 일정 횟수 이상이라면 정지 상태로 바꿔주고, 정지 날짜를 bannedUser에 넣어준다.
    private void handleReportedUser(User reportedUser) {
        int userWarnings = reportedUser.updateUserWarning();

        if (userWarnings == USER_DELETE_THRESHOLD) {
            reportedUser.updateUserStatus(UserStatus.DELETE);
        } else if (userWarnings == USER_BAN_THRESHOLD) {
            reportedUser.updateUserStatus(UserStatus.BAN);
            createBannedUser(reportedUser);
        }
    }

    // 정지된 회원의 정보를 bannedUser 엔티티에 생성하는 매서드
    private void createBannedUser(User bannedUser) {
        BannedUser newBannedUser = new BannedUser(bannedUser);
        bannedUserRepository.save(newBannedUser);
    }

    // 정지된 회원 일정 기간(10일)이 지나면 ACTIVE로 상태 변경
    // 매일 00시 00분 00초에 실행
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void ActiveUser() {
        // 현재 날짜로부터 10일 전 자정
        LocalDateTime tenDaysAgo = LocalDateTime.now().minusDays(9).toLocalDate().atStartOfDay();

        List<BannedUser> bannedUsersToBeActive = bannedUserRepository.findByBannedDateBefore(tenDaysAgo);

        for (BannedUser bannedUserToBeActive : bannedUsersToBeActive) {
            User user = bannedUserToBeActive.getUser();
            user.updateUserStatus(UserStatus.ACTIVE);
        }
    }
}