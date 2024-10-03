package com.developer.project.post.query.controller;

import com.developer.project.post.query.dto.ProjPostListResponseDTO;
import com.developer.project.post.query.dto.ProjPostResponseDTO;
import com.developer.project.post.query.service.ProjPostQueryService;
import com.developer.search.query.dto.SearchResultDTO;
import com.developer.search.query.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "project-post", description = "프로젝트 자랑 게시글 API")
@RequiredArgsConstructor
@RequestMapping("/proj")
@RestController
public class ProjPostQueryController {

    private final ProjPostQueryService projPostQueryService;
    private final SearchService searchService;

    @GetMapping("/post")
    @Operation(summary = "프로젝트 자랑 게시글 목록 조회", description = "등록되어 있는 프로젝트 게시글 목록을 조회합니다.")
    public ResponseEntity<List<ProjPostListResponseDTO>> readAll(@RequestParam( value = "page", defaultValue = "1") Integer page) {
        List<ProjPostListResponseDTO> projPostList = projPostQueryService.readAll(page);

        return ResponseEntity.ok(projPostList);
    }

    @GetMapping("/post/{projPostCode}")
    @Operation(summary = "프로젝트 자랑 게시글 상세 내용 조회", description = "등록되어 있는 프로젝트 게시글의 상세 내용을 조회합니다.")
    public ResponseEntity<ProjPostResponseDTO> readByCode(@PathVariable(value = "projPostCode") Long projPostCode) {
        ProjPostResponseDTO projPostResponseDTO = projPostQueryService.readByCode(projPostCode);

        return ResponseEntity.ok(projPostResponseDTO);
    }

    @GetMapping("/search/tag")
    @Operation(summary = "태그로 프로젝트 자랑 게시글 검색", description = "프로젝트 자랑 게시글에 달린 태그로 게시글을 검색합니다.")
    public ResponseEntity<List<ProjPostListResponseDTO>> readByTag(
            @RequestParam(value = "searchTag") String searchTag,
            @RequestParam( value = "page", defaultValue = "1") Integer page
            ) {
        List<ProjPostListResponseDTO> searchList = projPostQueryService.searchByTag(searchTag, page);

        return ResponseEntity.ok(searchList);
    }

    // 프로젝트 게시판 내에서 검색하기
    @GetMapping("/search")
    @Operation(summary = "프로젝트 자랑 게시판 검색", description = "키워드(keyword)를 포함하고 있는 프로젝트 자랑 게시글을 검색을 통해 조회합니다.")
    public ResponseEntity<List<SearchResultDTO>> searchProjPost(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page)
    {
        List<SearchResultDTO> projPostSearchResultDTO = searchService.search("proj", keyword, page);
        return ResponseEntity.ok(projPostSearchResultDTO);
    }
}
