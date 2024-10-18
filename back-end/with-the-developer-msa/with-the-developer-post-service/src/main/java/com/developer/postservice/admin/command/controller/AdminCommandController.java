package com.developer.postservice.admin.command.controller;

import com.developer.postservice.admin.command.service.AdminCommandService;
import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.common.success.SuccessCode;
import com.developer.postservice.recruit.command.entity.ApprStatus;
import com.developer.postservice.report.command.entity.ReportType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "admin", description = "관리자 기능 API")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCommandController {

    private final AdminCommandService adminCommandService;

    // ======== 채용공고 =========
    // 채용공고 등록 신청 승인/반려
    @PutMapping("/recruit/{recruitCode}")
    @Operation(summary = "채용공고 신청에 대한 처리(승인/반려)", description = "채용공고 신청에 대해 관리자가 승인 또는 반려합니다.")
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
    @PostMapping("/job-tag")
    @Operation(summary = "직무태그 등록", description = "직무태그 이름(jobTagName)을 이용하여 새로운 직무태그를 등록합니다.")
    public ResponseEntity<SuccessCode> createJobTag(@RequestParam(name = "jobTagName") String jobTagName) {
        adminCommandService.createJobTag(jobTagName);
        return ResponseEntity.ok(SuccessCode.JOB_TAG_CREATE_OK);
    }

    // 직무태그 삭제하기
    @DeleteMapping("/job-tag/{jobTagName}")
    @Operation(summary = "직무태그 삭제", description = "등록되어 있는 직무태그를 삭제합니다.")
    public ResponseEntity<SuccessCode> deleteJobTag(@PathVariable(name = "jobTagName") String jobTagName) {
        adminCommandService.deleteJobTag(jobTagName);
        return ResponseEntity.ok(SuccessCode.JOB_TAG_DELETE_OK)   ;
    }


    // ======= 신고 사유 카테고리 =======
    // ReportReasonCategory(신고 사유 카테고리) 추가
    @PostMapping("/report-reason-category")
    @Operation(summary = "신고 사유 카테고리 추가", description = "신고 사유 카테고리 이름(category)을 통해 새로운 신고 사유 카테고리를 등록합니다.")
    public ResponseEntity<SuccessCode> createReportReasonCategory(@RequestParam(name = "category") String category) {
        adminCommandService.createReportReasonCategory(category);
        return ResponseEntity.ok(SuccessCode.REPORT_REASON_CATEGORY_CREATE_OK);
    }

    // 신고 사유 카테고리 삭제
    @DeleteMapping("/report-reason-category")
    @Operation(summary = "신고 사유 카테고리 삭제", description = "등록되어 있는 신고 사유 카테고리를 삭제합니다.")
    public ResponseEntity<SuccessCode> deleteReportReasonCategory(@RequestParam(name = "category") String category) {
        adminCommandService.deleteReportReasonCategory(category);
        return ResponseEntity.ok(SuccessCode.REPORT_REASON_CATEGORY_DELETE_OK);
    }

    // ====== 신고 처리 ======
    // 관리자가 수동으로 신고 처리 (게시물 block)
    @PutMapping("/report/{repoCode}")
    @Operation(summary = "수동 신고 처리", description = "관리자가 수동으로 신고를 처리(게시물 삭제)를 합니다.")
    public ResponseEntity<SuccessCode> deleteReportPost (
            @PathVariable(name = "repoCode") Long repoCode,
            @RequestParam(name = "reportTypePara") String reportTypePara
    ) {
        ReportType reportType;

        // Enum으로 변환
        try {
            reportType = ReportType.valueOf(reportTypePara.toUpperCase());
        } catch(IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_VALUE);
        }

        adminCommandService.deletePostAndUpdateStatus(repoCode, reportType);
        return ResponseEntity.ok(SuccessCode.REPORT_HANDLE_OK);
    }
}