package com.developer.search.query.controller;

import com.developer.search.query.dto.SearchIntegratedResultDTO;
import com.developer.search.query.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    // 통합 검색
    @GetMapping
    public ResponseEntity<List<SearchIntegratedResultDTO>> searchIntegrated(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page)
    {
        List<SearchIntegratedResultDTO> integratedSearchResultDTO = searchService.searchIntegrated(keyword, page);
        return ResponseEntity.ok(integratedSearchResultDTO);
    }
}
