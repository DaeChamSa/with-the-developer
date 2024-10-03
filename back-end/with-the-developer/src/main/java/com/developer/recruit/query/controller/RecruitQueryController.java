package com.developer.recruit.query.controller;

import com.developer.recruit.query.dto.RecruitDetailReadDTO;
import com.developer.recruit.query.dto.RecruitListReadDTO;
import com.developer.recruit.query.service.RecruitQueryService;
import com.developer.search.query.dto.SearchResultDTO;
import com.developer.search.query.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "recruit", description = "채용공고 API")
@Slf4j
@RestController
@RequestMapping("/recruit")
@RequiredArgsConstructor
public class RecruitQueryController {

    private final RecruitQueryService recruitQueryService;
    private final SearchService searchService;

    // 등록된 채용공고 목록 조회
    @GetMapping("/list")
    @Operation(summary = "등록된 채용공고 목록 조회", description = "등록된 채용공고 목록을 조회합니다.")
    public ResponseEntity<List<RecruitListReadDTO>> readRecruitList(@RequestParam(defaultValue = "1") Integer page) {
        List<RecruitListReadDTO> recruitList = recruitQueryService.readRecruitList(page);

        return ResponseEntity.ok(recruitList);
    }

    // 등록된 채용공고 상세 내용 조회
    @GetMapping("/detail/{recruitCode}")
    @Operation(summary = "등록된 채용공고 상세 내용 조회", description = "등록된 채용공고의 상세 내용을 조회합니다.")
    public ResponseEntity<RecruitDetailReadDTO> readRecruitDetail(@PathVariable long recruitCode) {
        RecruitDetailReadDTO recruitDetailReadDTO = recruitQueryService.readRecruitDetailById(recruitCode);

        return ResponseEntity.ok(recruitDetailReadDTO);
    }

    @GetMapping("/search/tag")
    @Operation(summary = "태그를 통한 채용공고 검색", description = "직무태그를 통해 채용공고 목록을 검색합니다.")
    public ResponseEntity<List<RecruitListReadDTO>> searchRecruitList(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "searchTag") String searchTag
    ) {
        log.info(searchTag.toString());
        List<RecruitListReadDTO> searchList = recruitQueryService.searchRecruitByTag(searchTag, page);

        return ResponseEntity.ok(searchList);
    }

    // 채용공고 게시판 내에서 검색하기
    @GetMapping("/search")
    @Operation(summary = "채용공고 게시물 검색", description = "키워드(keyword)를 포함하고 있는 채용공고 게시물을 검색을 통해 조회합니다.")
    public ResponseEntity<List<SearchResultDTO>> searchRecruit(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page)
    {
        List<SearchResultDTO> recruitSearchResultDTO = searchService.search("recruit", keyword, page);
        return ResponseEntity.ok(recruitSearchResultDTO);
    }
}
