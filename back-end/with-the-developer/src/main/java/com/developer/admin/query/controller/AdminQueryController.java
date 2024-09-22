package com.developer.admin.query.controller;

import com.developer.admin.query.dto.RecruitApplyDetailReadDTO;
import com.developer.admin.query.dto.RecruitApplyListReadDTO;
import com.developer.admin.query.service.AdminQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminQueryController {

    private final AdminQueryService adminQueryService;

    // 채용공고 등록 신청 내역 목록 조회
    @GetMapping("/recruit/applyList")
    public ResponseEntity<List<RecruitApplyListReadDTO>> readApplyRecruitList() {
        List<RecruitApplyListReadDTO> recruitApplyList = adminQueryService.readRecruitApplyList();
        return ResponseEntity.ok(recruitApplyList);
    }

    // 채용공고 등록 신청 상세내역 조회
    @GetMapping("/recruit/applyDetail/{recruitCode}")
    public ResponseEntity<RecruitApplyDetailReadDTO> readApplyDetail(@PathVariable long recruitCode) {
        RecruitApplyDetailReadDTO recruitApplyDetailReadDTO = adminQueryService.readRecruitApplyDetailById(recruitCode);

        return ResponseEntity.ok(recruitApplyDetailReadDTO);
    }
}