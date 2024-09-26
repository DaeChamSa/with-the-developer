package com.developer.admin.command.service;

import com.developer.admin.command.dto.AdminRecruitApplyUpdateDTO;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.jobTag.entity.JobTag;
import com.developer.jobTag.repository.JobTagRepository;
import com.developer.recruit.command.entity.Recruit;
import com.developer.recruit.command.entity.RecruitStatus;
import com.developer.recruit.command.repository.RecruitRepository;
import com.developer.report.command.entity.ReportReasonCategory;
import com.developer.report.command.repository.ReportReasonCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminCommandService {

    private final RecruitRepository recruitRepository;
    private final JobTagRepository jobTagRepository;
    private final ReportReasonCategoryRepository reportReasonCategoryRepository;

    // 채용공고 등록 승인 처리 (승인/반려)
    @Transactional
    public void updateRecruitApply(Long recruitCode, AdminRecruitApplyUpdateDTO adminRecruitApplyUpdateDTO) {
        Recruit recruit = recruitRepository.findById(recruitCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 현재 시간
        LocalDateTime now = LocalDateTime.now();

        // 게시 날짜를 승인 시점으로 UPDATE
        adminRecruitApplyUpdateDTO.setRecruitPostDate(now);

        // 모집기간에 따른 상태값 넣어주기(모집전, 모집중, 모집완료)
        if (now.isBefore(recruit.getRecruitStart())) {
            adminRecruitApplyUpdateDTO.setRecruitStatus(RecruitStatus.UPCOMING);
        } else if (now.isAfter(recruit.getRecruitEnd())) {
            adminRecruitApplyUpdateDTO.setRecruitStatus(RecruitStatus.COMPLETED);
        } else {
            adminRecruitApplyUpdateDTO.setRecruitStatus(RecruitStatus.ACTIVE);
        }

        // 승인, 반려 처리는 req로 받은 DTO의 recruitApprStatus 값 그대로 save
        recruit.updateRecruitApply(adminRecruitApplyUpdateDTO);

        recruitRepository.save(recruit);
    }

    // 직무태그 등록하기
    @Transactional
    public void createJobTag(String jobTagName) {
        // jobTagName이 null이거나 입력되지 않았을 때의 예외처리
        if (jobTagName == null || jobTagName.trim().isEmpty()) {
            throw new CustomException(ErrorCode.MISSING_VALUE);
        }

        // 등록하려는 jobTagName이 이미 존재할 경우의 예외처리
        if (jobTagRepository.existsByJobTagName(jobTagName)) {
            throw new CustomException(ErrorCode.DUPLICATE_JOB_TAG);
        }

        JobTag JobTag = new JobTag(jobTagName);
        jobTagRepository.save(JobTag);
    }


    // 신고 사유 카테고리 추가하기
    public void createReportReasonCategory(String category) {
        // category가 null이거나 입력되지 않았을 때의 예외처리
        if (category == null || category.trim().isEmpty()) {
            throw new CustomException(ErrorCode.MISSING_VALUE);
        }

        // 등록하려는 category가 이미 존재할 경우의 예외처리
        if (jobTagRepository.existsByJobTagName(category)) {
            throw new CustomException(ErrorCode.DUPLICATE_VALUE);
        }

        ReportReasonCategory reportReasonCategory = new ReportReasonCategory(category);
        reportReasonCategoryRepository.save(reportReasonCategory);
    }
}