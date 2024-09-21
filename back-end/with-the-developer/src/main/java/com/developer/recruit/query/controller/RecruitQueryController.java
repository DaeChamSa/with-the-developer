package com.developer.recruit.query.controller;

import com.developer.recruit.query.dto.RecruitDetailReadDTO;
import com.developer.recruit.query.dto.RecruitListReadDTO;
import com.developer.recruit.query.service.RecruitQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recruit")
@RequiredArgsConstructor
public class RecruitQueryController {
    private final RecruitQueryService recruitQueryService;

    // 등록된 채용공고 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<RecruitListReadDTO>> readRecruitList() {
        List<RecruitListReadDTO> recruitList = recruitQueryService.readRecruitList();

        return ResponseEntity.ok(recruitList);
    }

    // 등록된 채용공고 상세 내역 조회
    @GetMapping("/detail/{recruitCode}")
    public ResponseEntity<RecruitDetailReadDTO> readRecruitDetail(@PathVariable long recruitCode) {
        RecruitDetailReadDTO recruitDetailReadDTO = recruitQueryService.readRecruitDetailById(recruitCode);

        return ResponseEntity.ok(recruitDetailReadDTO);
    }

}
