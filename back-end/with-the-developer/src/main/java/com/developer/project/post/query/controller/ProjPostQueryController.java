package com.developer.project.post.query.controller;

import com.developer.project.post.query.dto.ProjPostListResponseDTO;
import com.developer.project.post.query.dto.ProjPostResponseDTO;
import com.developer.project.post.query.service.ProjPostQueryService;
import com.developer.search.query.dto.SearchResultDTO;
import com.developer.search.query.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/proj")
@RestController
public class ProjPostQueryController {

    private final ProjPostQueryService projPostQueryService;
    private final SearchService searchService;

    @GetMapping("/post")
    public ResponseEntity<List<ProjPostListResponseDTO>> readAll(@RequestParam( value = "page", defaultValue = "1") Integer page) {
        List<ProjPostListResponseDTO> projPostList = projPostQueryService.readAll(page);

        return ResponseEntity.ok(projPostList);
    }

    @GetMapping("/post/{projPostCode}")
    public ResponseEntity<ProjPostResponseDTO> readByCode(@PathVariable(value = "projPostCode") Long projPostCode) {
        ProjPostResponseDTO projPostResponseDTO = projPostQueryService.readByCode(projPostCode);

        return ResponseEntity.ok(projPostResponseDTO);
    }

    @GetMapping("/search/tag")
    public ResponseEntity<List<ProjPostListResponseDTO>> readByTag(
            @RequestParam(value = "searchTag") String searchTag,
            @RequestParam( value = "page", defaultValue = "1") Integer page
            ) {
        List<ProjPostListResponseDTO> searchList = projPostQueryService.searchByTag(searchTag, page);

        return ResponseEntity.ok(searchList);
    }

    // 프로젝트 게시판 내에서 검색하기
    @GetMapping("/search")
    public ResponseEntity<List<SearchResultDTO>> searchProjPost(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page)
    {
        List<SearchResultDTO> projPostSearchResultDTO = searchService.search("proj", keyword, page);
        return ResponseEntity.ok(projPostSearchResultDTO);
    }
}
