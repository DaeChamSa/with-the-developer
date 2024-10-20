package com.developer.postservice.admin.query.controller;

import com.developer.postservice.admin.query.dto.RecruitApplyDetailReadDTO;
import com.developer.postservice.admin.query.dto.RecruitApplyListReadDTO;
import com.developer.postservice.admin.query.dto.ReportDetailReadDTO;
import com.developer.postservice.admin.query.dto.ReportListReadDTO;
import com.developer.postservice.admin.query.service.AdminQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "admin", description = "관리자 기능 API")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminQueryController {

    private final AdminQueryService adminQueryService;


    // 채용공고 등록 신청 내역 목록 조회
    @GetMapping("/recruit")
    @Operation(summary = "채용공고 등록 신청 목록 조회", description = "채용공고 등록 신청이 들어온 목록을 조회합니다.")
    public ResponseEntity<List<RecruitApplyListReadDTO>> readApplyRecruitList(@RequestParam(defaultValue = "1") Integer page) {
        List<RecruitApplyListReadDTO> recruitApplyList = adminQueryService.readRecruitApplyList(page);
        return ResponseEntity.ok(recruitApplyList);
    }

    // 채용공고 등록 신청 상세내역 조회
    @GetMapping("/recruit/{recruitCode}")
    @Operation(summary = "채용공고 등록 신청 상세내용 조회", description = "등록 신청이 들어온 채용공고의 상세내용을 조회합니다.")
    public ResponseEntity<RecruitApplyDetailReadDTO> readApplyDetail(@PathVariable Long recruitCode) {
        RecruitApplyDetailReadDTO recruitApplyDetailReadDTO = adminQueryService.readRecruitApplyDetailById(recruitCode);

        return ResponseEntity.ok(recruitApplyDetailReadDTO);
    }

    // 신고 목록 조회하기
    @GetMapping("/report")
    @Operation(summary = "신고 목록 조회", description = "들어온 신고의 목록을 조회합니다.")
    public ResponseEntity<List<ReportListReadDTO>> readReportList(@RequestParam(defaultValue = "1") Integer page) {
        List<ReportListReadDTO> reportListReadDTO = adminQueryService.readReportList(page);
        return ResponseEntity.ok(reportListReadDTO);
    }

    // 신고 상세 내용 조회하기
    @GetMapping("/report/{repoCode}")
    @Operation(summary = "신고 상세 내용 조회", description = "들어온 신고의 상세내용을 조회합니다.")
    public ResponseEntity<ReportDetailReadDTO> readReportDetail(@PathVariable Long repoCode) {
        ReportDetailReadDTO reportDetailReadDTO  = adminQueryService.readReportDetailById(repoCode);
        return ResponseEntity.ok(reportDetailReadDTO);
    }
}
