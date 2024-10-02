package com.developer.comu.post.query.controller;

import com.developer.comu.post.query.service.ComuPostQueryService;
import com.developer.comu.post.query.dto.ComuPostResponseDTO;
import com.developer.search.query.dto.SearchResultDTO;
import com.developer.search.query.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "community-post", description = "커뮤니티 게시글 API")
@RequiredArgsConstructor
@RequestMapping("/comu")
@RestController
@Slf4j
public class ComuPostQueryController {

    private final ComuPostQueryService comuPostQueryService;
    private final SearchService searchService;
    
    @GetMapping("/postlist")
    @Operation(summary = "커뮤니티 게시글 목록 조회", description = "등록되어 있는 커뮤니티 게시글 목록을 조회합니다.")
    public ResponseEntity<List<ComuPostResponseDTO>> selectAllComuPost(@RequestParam(defaultValue = "1") Integer page) {
        List<ComuPostResponseDTO> comuList = comuPostQueryService.selectAllComuPost(page);

        return ResponseEntity.ok(comuList);
    }

    @GetMapping("/postlist/{comuPostCode}")
    @Operation(summary = "커뮤니티 게시글 조회", description = "등록되어 있는 커뮤니티 게시글을 조회합니다.")
    public ResponseEntity<ComuPostResponseDTO> selectComuPostByCode(@PathVariable(name = "comuPostCode") Long comuPostCode) {
        ComuPostResponseDTO comuPostResponseDTO = comuPostQueryService.selectComuPostByCode(comuPostCode);

        return ResponseEntity.ok(comuPostResponseDTO);
    }

    // 커뮤니티 게시판 내에서 검색하기
    @GetMapping("/search")
    @Operation(summary = "커뮤니티 게시글 검색", description = "키워드(keyword)를 포함하고 있는 커뮤니티 게시글을 검색을 통해 조회합니다.")
    public ResponseEntity<List<SearchResultDTO>> searchComuPost(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page)
    {
        List<SearchResultDTO> comuPostSearchResultDTO = searchService.search("comu", keyword, page);
        return ResponseEntity.ok(comuPostSearchResultDTO);
    }

}
