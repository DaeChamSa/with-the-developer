package com.developer.recruit.query.controller;

import com.developer.recruit.query.dto.RecruitDetailReadDTO;
import com.developer.recruit.query.dto.RecruitListReadDTO;
import com.developer.recruit.query.service.RecruitQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/recruit")
@RequiredArgsConstructor
public class RecruitQueryController {
    private final RecruitQueryService recruitQueryService;

    // 등록된 채용공고 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<RecruitListReadDTO>> readRecruitList(@RequestParam(defaultValue = "1") Integer page) {
        List<RecruitListReadDTO> recruitList = recruitQueryService.readRecruitList(page);

        return ResponseEntity.ok(recruitList);
    }

    // 등록된 채용공고 상세 내역 조회
    @GetMapping("/detail/{recruitCode}")
    public ResponseEntity<RecruitDetailReadDTO> readRecruitDetail(@PathVariable long recruitCode) {
        RecruitDetailReadDTO recruitDetailReadDTO = recruitQueryService.readRecruitDetailById(recruitCode);

        return ResponseEntity.ok(recruitDetailReadDTO);
    }

    @GetMapping("/search/tag")
    public ResponseEntity<List<RecruitListReadDTO>> searchRecruitList(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "searchTags") List<String> searchTags
    ) {
        log.info(searchTags.toString());
        List<RecruitListReadDTO> searchList = recruitQueryService.searchRecruitByTag(searchTags, page);

        return ResponseEntity.ok(searchList);
    }


}
