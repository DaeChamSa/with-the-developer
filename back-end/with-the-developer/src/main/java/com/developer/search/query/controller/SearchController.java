package com.developer.search.query.controller;

import com.developer.search.query.dto.SearchIntegratedResultDTO;
import com.developer.search.query.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "search", description = "통합검색 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    // 통합 검색
    @GetMapping
    @Operation(summary = "통합검색", description = "키워드(keyword)를 포함하고 있는 게시글을 검색을 통해 조회합니다.")
    public ResponseEntity<List<SearchIntegratedResultDTO>> searchIntegrated(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page)
    {
        List<SearchIntegratedResultDTO> integratedSearchResultDTO = searchService.searchIntegrated(keyword, page);
        return ResponseEntity.ok(integratedSearchResultDTO);
    }
}
