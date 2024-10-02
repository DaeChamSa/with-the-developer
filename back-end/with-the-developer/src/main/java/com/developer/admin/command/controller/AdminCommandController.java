package com.developer.admin.command.controller;

import com.developer.admin.command.service.AdminCommandService;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.common.success.SuccessCode;
import com.developer.recruit.command.entity.ApprStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCommandController {

    private final AdminCommandService adminCommandService;

    // ======== 채용공고 =========
    // 채용공고 등록 신청 승인/반려
    @PutMapping("/recruit/approve/{recruitCode}")
    public ResponseEntity<SuccessCode> apprRecruitApply(
            @PathVariable Long recruitCode,
            @RequestParam String status
            )
    {
        ApprStatus apprStatus;

        // Enum으로 변환
        try {
            apprStatus = ApprStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e){
            throw new CustomException(ErrorCode.INVALID_VALUE);
        }

        adminCommandService.updateRecruitApply(recruitCode, apprStatus);
        return ResponseEntity.ok(SuccessCode.RECRUIT_APPLY_APPR_OK);
    }


    // ======== 직무태그 ========
    // 직무태그 등록하기
    @PostMapping("/job-tag/create")
    public ResponseEntity<SuccessCode> createJobTag(@RequestParam(name = "jobTagName") String jobTagName) {
        adminCommandService.createJobTag(jobTagName);
        return ResponseEntity.ok(SuccessCode.JOB_TAG_CREATE_OK);
    }

    // 직무태그 삭제하기
    @DeleteMapping("/job-tag/delete/{jobTagName}")
    public ResponseEntity<SuccessCode> deleteJobTag(@PathVariable String jobTagName) {
        adminCommandService.deleteJobTag(jobTagName);
        return ResponseEntity.ok(SuccessCode.JOB_TAG_DELETE_OK)   ;
    }


    // ======= 신고 사유 카테고리 =======
    // ReportReasonCategory(신고 사유 카테고리) 추가
    @PostMapping("/report-reason-category/create")
    public ResponseEntity<SuccessCode> createReportReasonCategory(@RequestParam String category) {
        adminCommandService.createReportReasonCategory(category);
        return ResponseEntity.ok(SuccessCode.REPORT_REASON_CATEGORY_CREATE_OK);
    }

    // 신고 사유 카테고리 삭제
    @DeleteMapping("/report-reason-category/delete")
    public ResponseEntity<SuccessCode> deleteReportReasonCategory(@RequestParam String category) {
        adminCommandService.deleteReportReasonCategory(category);
        return ResponseEntity.ok(SuccessCode.REPORT_REASON_CATEGORY_DELETE_OK);
    }

    // ====== 신고 처리 ======
    // 관리자가 수동으로 신고 처리 (게시물 block)
    @PutMapping("/report/delete-post/{repoCode}")
    public ResponseEntity<SuccessCode> deleteReportPost(@PathVariable Long repoCode) {
        adminCommandService.deletePostAndUpdateStatus(repoCode);
        return ResponseEntity.ok(SuccessCode.REPORT_HANDLE_OK);
    }
}