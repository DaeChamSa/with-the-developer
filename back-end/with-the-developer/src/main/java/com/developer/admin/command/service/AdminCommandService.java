package com.developer.admin.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.comu.post.command.entity.ComuPost;
import com.developer.comu.post.command.repository.ComuPostRepository;
import com.developer.jobTag.command.entity.JobTag;
import com.developer.jobTag.command.repository.JobTagRepository;
import com.developer.noti.command.application.dto.NotiCommentCreateDTO;
import com.developer.noti.command.application.dto.NotiRecruitCreateDTO;
import com.developer.noti.command.application.service.NotiCommandService;
import com.developer.noti.command.domain.aggregate.PostType;
import com.developer.project.post.command.domain.repository.ProjPostRepository;
import com.developer.recruit.command.entity.ApprStatus;
import com.developer.recruit.command.entity.Recruit;
import com.developer.recruit.command.entity.RecruitStatus;
import com.developer.recruit.command.repository.RecruitRepository;
import com.developer.report.command.entity.Report;
import com.developer.report.command.entity.ReportReasonCategory;
import com.developer.report.command.entity.ReportType;
import com.developer.report.command.repository.ReportReasonCategoryRepository;
import com.developer.report.command.repository.ReportRepository;
import com.developer.team.post.command.entity.TeamPost;
import com.developer.team.post.command.repository.TeamPostRepository;
import com.developer.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCommandService {

    private final RecruitRepository recruitRepository;
    private final JobTagRepository jobTagRepository;
    private final ReportReasonCategoryRepository reportReasonCategoryRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final TeamPostRepository teamPostRepository;
    private final ProjPostRepository projPostRepository;
    private final ComuPostRepository comuPostRepository;
    private final NotiCommandService notiCommandService;

    // 채용공고 등록 승인 처리 (승인/반려)
    @Transactional
    public void updateRecruitApply(Long recruitCode, ApprStatus apprStatus) {
        Recruit recruit = recruitRepository.findById(recruitCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 승인상태 업데이트
        recruit.updateApprStatus(apprStatus);

        // 승인일 경우 recruitStatus, recruitPostDate에 값 업데이트
        if (apprStatus == ApprStatus.APPROVE) {
            // 현재 시간
            LocalDateTime now = LocalDateTime.now();

            // 모집기간에 따른 상태값 넣어주기(모집전, 모집중, 모집완료)
            RecruitStatus recruitStatus;
            if (now.isBefore(recruit.getRecruitStart())) {
                recruitStatus = RecruitStatus.UPCOMING;
            } else if (now.isAfter(recruit.getRecruitEnd())) {
                recruitStatus = RecruitStatus.COMPLETED;
            } else {
                recruitStatus = RecruitStatus.ACTIVE;
            }

            recruit.updateRecruit(now, recruitStatus);

            // 채용공고 승인시 알림 생성 (알림받을 사용자 코드, 채용공고 코드)
            NotiRecruitCreateDTO notiRecruitCreateDTO =
                    new NotiRecruitCreateDTO(
                            recruit.getUser().getUserCode()
                            , recruitCode
                    );

            notiCommandService.addAcceptEvent(notiRecruitCreateDTO);
        } else {

            // 채용공고 반려시 알림 생성 (알림받을 사용자 코드, 채용공고 코드)
            NotiRecruitCreateDTO notiRecruitCreateDTO =
                    new NotiRecruitCreateDTO(
                            recruit.getUser().getUserCode()
                            , recruitCode
                    );

            notiCommandService.addRejectEvent(notiRecruitCreateDTO);
        }

        recruitRepository.save(recruit);

    }

    // 직무태그 추가하기
    @Transactional
    public void createJobTag(String jobTagName) {
        // jobTagName이 null이거나 입력되지 않았을 때의 예외처리
        if (jobTagName == null || jobTagName.trim().isEmpty()) {
            throw new CustomException(ErrorCode.MISSING_VALUE);
        }

        // 등록하려는 jobTagName이 이미 존재할 경우의 예외처리
        if (jobTagRepository.existsByJobTagName(jobTagName)) {
            throw new CustomException(ErrorCode.DUPLICATE_VALUE);
        }

        JobTag jobTag = new JobTag(jobTagName);
        jobTagRepository.save(jobTag);
    }

    // 직무태그 삭제하기
    @Transactional
    public void deleteJobTag(String jobTagName) {
        // jobTagName이 null이거나 입력되지 않았을 때의 예외처리
        if (jobTagName == null || jobTagName.trim().isEmpty()) {
            throw new CustomException(ErrorCode.MISSING_VALUE);
        }

        if (jobTagRepository.existsByJobTagName(jobTagName)) {
            jobTagRepository.deleteByJobTagName(jobTagName);
        } else {
            // 존재하지 않는 jobTagName을 삭제하려는 경우의 예외처리
            throw new CustomException(ErrorCode.NOT_FOUND_JOB_TAG);
        }
    }

    // 신고 사유 카테고리 추가하기
    @Transactional
    public void createReportReasonCategory(String category) {
        // category가 null이거나 입력되지 않았을 때의 예외처리
        if (category == null || category.trim().isEmpty()) {
            throw new CustomException(ErrorCode.MISSING_VALUE);
        }

        // 등록하려는 category가 이미 존재할 경우의 예외처리
        if (reportReasonCategoryRepository.existsByRepoReasonName(category)) {
            throw new CustomException(ErrorCode.DUPLICATE_VALUE);
        }

        ReportReasonCategory reportReasonCategory = new ReportReasonCategory(category);
        reportReasonCategoryRepository.save(reportReasonCategory);
    }

    // 신고 사유 카테고리 삭제하기
    @Transactional
    public void deleteReportReasonCategory(String category) {
        if (reportReasonCategoryRepository.existsByRepoReasonName(category)) {
            reportReasonCategoryRepository.deleteByRepoReasonName(category);
        } else {
            throw new CustomException(ErrorCode.NOT_FOUND_REPORT_REASON_CATEGORY);
        }
    }

    // 회원 신고 처리(수동으로 게시물 block)
    @Transactional
    public void deletePostAndUpdateStatus(Long repoCode) {
        Report report = reportRepository.findById(repoCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REPORT));
        ReportType reportType;
        if (report.getTeamPost() != null) {
            TeamPost teamPost = report.getTeamPost();
            teamPostRepository.delete(teamPost);

            reportType = ReportType.TEAMPOST;
            updateRepoStatusAndResolveDate(report, reportType);
        } else if (report.getProjPost() != null) {
            Long projPostCode = report.getProjPost().getProjPostCode();
            projPostRepository.deleteById(projPostCode);

            reportType = ReportType.PROJPOST;
            updateRepoStatusAndResolveDate(report, reportType);
        } else if (report.getRecruit() != null) {
            Recruit recruit = report.getRecruit();
            recruit.updateRecruitStatus(RecruitStatus.DELETE);

            reportType = ReportType.RECRUIT;
            updateRepoStatusAndResolveDate(report, reportType);
        } else if (report.getComuPost() != null) {
            ComuPost comuPost = report.getComuPost();
            comuPostRepository.delete(comuPost);

            reportType = ReportType.COMU;
            updateRepoStatusAndResolveDate(report, reportType);
        }
    }

    public List<Report> getListToBeApproved(Report report, ReportType reportType) {
        switch(reportType) {
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

    private void updateRepoStatusAndResolveDate(Report report, ReportType reportType) {
        List<Report> reportsToBeApproved = getListToBeApproved(report, reportType);

        for(Report reportToBeApproved:reportsToBeApproved) {
            reportToBeApproved.updateRepoStatus(ApprStatus.APPROVE);
            reportToBeApproved.updateReportResolveDate();
        }
    }
}