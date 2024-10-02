package com.developer.report.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.comu.post.command.entity.ComuPost;
import com.developer.comu.post.command.repository.ComuPostRepository;
import com.developer.project.post.command.domain.aggregate.ProjPost;
import com.developer.project.post.command.domain.repository.ProjPostRepository;
import com.developer.recruit.command.entity.ApprStatus;
import com.developer.recruit.command.entity.Recruit;
import com.developer.recruit.command.entity.RecruitStatus;
import com.developer.recruit.command.repository.RecruitRepository;
import com.developer.report.command.dto.ReportCreateDTO;
import com.developer.report.command.dto.ReportCreateResultDTO;
import com.developer.report.command.entity.Report;
import com.developer.report.command.entity.ReportReasonCategory;
import com.developer.report.command.entity.ReportType;
import com.developer.report.command.repository.ReportReasonCategoryRepository;
import com.developer.report.command.repository.ReportRepository;
import com.developer.team.post.command.entity.TeamPost;
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
    private static final int POST_BLOCK_THRESHOLD = 5;

    // 신고 생성 후 처리 메소드
    @Transactional
    public Long createAndHandleReport(ReportCreateDTO reportCreateDTO, Long userCode, Long postCode, ReportType reportType) {

        // 신고 생성하기
        ReportCreateResultDTO reportCreateResult = createReport(reportCreateDTO, userCode, postCode, reportType);
        Report report = reportCreateResult.getReport();
        User reportedUser = reportCreateResult.getReportedUser();

        // 신고가 새로 생성되었으니 저장
        reportRepository.save(report);
        userRepository.save(reportedUser);

        // 일정 횟수 이상 신고를 받은 게시물이나 회원에 대한 신고 처리(게시물 삭제, 회원 정지)
        handleReport(report, reportedUser, reportType);

        return reportCreateResult.getReport().getRepoCode();
    }

    public ReportCreateResultDTO createReport(ReportCreateDTO reportCreateDTO, Long userCode, Long postCode, ReportType reportType) {

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

        // 신고 당한 회원
        User reportedUser;

        // 신고당한 게시물의 code를 report 엔티티에 저장
        switch (reportType) {
            case COMU:
                ComuPost comuPost = comuPostRepository.findById(postCode)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
                report.updateComuCode(comuPost);
                reportedUser = comuPost.getUser();
                break;
            case RECRUIT:
                Recruit recruit = recruitRepository.findById(postCode)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
                report.updateRecruitCode(recruit);
                reportedUser = recruit.getUser();
                break;
            case TEAMPOST:
                TeamPost teamPost = teamPostRepository.findById(postCode)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
                report.updateTeamPostCode(teamPost);
                reportedUser = teamPost.getUser();
                break;
            case PROJPOST:
                ProjPost projPost = projPostRepository.findById(postCode)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
                report.updateProjPostCode(projPost);
                reportedUser = userRepository.findById(projPost.getUserCode())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
                break;
            default:
                throw new CustomException(ErrorCode.NO_VALID_VALUE);
        }

        return new ReportCreateResultDTO(report, reportedUser);
    }

    // 신고에 대한 처리
    public void handleReport(Report report, User reportedUser, ReportType reportType) {
        // 해당 게시물의 신고 횟수를 가져온다.
        int reportedCount = getReportedCount(report, reportType);

        if (reportedCount >= POST_BLOCK_THRESHOLD) {
            // 일정 횟수 이상 신고가 들어왔다면 그 게시물을 block 처리한다.
            deletePost(report, reportType);
            // 일정 횟수 이상 신고가 들어와 자동으로 처리된 신고의 상태를 APPROVE로 변경하고, 그 시간을 엔티티에 저장한다.
            updateReportApproveAndResolveDateAuto(report, reportType);
        }

        // 신고된 게시물의 게시물 작성자의 지금까지 받은 신고 횟수가 일정 횟수 이상이라면 정지 상태로 바꿔주고, 정지 날짜를 bannedUser에 넣어준다.
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

    // 해당 게시물이 지금까지 몇 번 신고되었는지 가져오는 메서드
    private int getReportedCount(Report report, ReportType reportType) {
        switch (reportType) {
            case COMU:
                return Math.toIntExact(reportRepository.countByComuPost(report.getComuPost()));
            case RECRUIT:
                return Math.toIntExact(reportRepository.countByRecruit(report.getRecruit()));
            case TEAMPOST:
                return Math.toIntExact(reportRepository.countByTeamPost(report.getTeamPost()));
            case PROJPOST:
                return Math.toIntExact(reportRepository.countByProjPost(report.getProjPost()));
            default:
                throw new CustomException(ErrorCode.NO_VALID_VALUE); // 잘못된 타입 처리
        }
    }

    // 신고가 처리되어 APPROVE로 상태를 변경해야 하는 report들을 가져오는 메서드
    private List<Report> getListToBeApproved(Report report, ReportType reportType) {
        switch (reportType) {
            case COMU:
                return reportRepository.findByComuPost(report.getComuPost());
            case RECRUIT:
                return reportRepository.findByRecruit(report.getRecruit());
            case TEAMPOST:
                return reportRepository.findByTeamPost(report.getTeamPost());
            case PROJPOST:
                return reportRepository.findByProjPost(report.getProjPost());
            default:
                throw new CustomException(ErrorCode.NO_VALID_VALUE); // 잘못된 타입 처리
        }
    }

    // 게시물의 repoStatus를 APPROVE로 변경하고 그 시간을 저장하는 메서드
    private void updateReportApproveAndResolveDateAuto(Report report, ReportType reportType) {
        List<Report> listToBeApproved = getListToBeApproved(report, reportType);
        for (Report toBeApproved : listToBeApproved) {
            toBeApproved.updateRepoStatus(ApprStatus.APPROVE);
            toBeApproved.updateReportResolveDate();
        }
    }

    // 게시물을 삭제(상태 변경)하는 메서드
    private void deletePost(Report report, ReportType reportType) {
        switch (reportType) {
            case COMU:
                ComuPost comuPost = report.getComuPost();
                comuPostRepository.delete(comuPost);
                break;
            case RECRUIT:
                Recruit recruit = report.getRecruit();
                recruit.updateRecruitStatus(RecruitStatus.DELETE);
                break;
            case TEAMPOST:
                TeamPost teamPost = report.getTeamPost();
                teamPostRepository.delete(teamPost);
                break;
            case PROJPOST:
                Long postCode = report.getProjPost().getProjPostCode();
                projPostRepository.deleteById(postCode);
                break;
            default:
                throw new CustomException(ErrorCode.NO_VALID_VALUE); // 잘못된 타입 처리
        }
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