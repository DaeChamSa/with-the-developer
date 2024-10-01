package com.developer.recruit.query.controller;

import com.developer.recruit.query.dto.RecruitDetailReadDTO;
import com.developer.recruit.query.dto.RecruitListReadDTO;
import com.developer.recruit.query.service.RecruitQueryService;
import com.developer.search.query.dto.SearchResultDTO;
import com.developer.search.query.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruit")
@RequiredArgsConstructor
public class RecruitQueryController {

    private final RecruitQueryService recruitQueryService;
    private final SearchService searchService;

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

    // 채용공고 게시판 내에서 검색하기
    @GetMapping("/search")
    public ResponseEntity<List<SearchResultDTO>> searchRecruit(@RequestParam String keyword,
                                               @RequestParam(defaultValue = "1") Integer page)
    {
        List<SearchResultDTO> recruitSearchResultDTO = searchService.search("recruit", keyword, page);
        return ResponseEntity.ok(recruitSearchResultDTO);
    }
}
