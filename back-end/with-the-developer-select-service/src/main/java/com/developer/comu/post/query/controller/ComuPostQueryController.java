package com.developer.comu.post.query.controller;

import com.developer.comu.post.query.dto.ComuPostResponseDTO;
import com.developer.comu.post.query.service.ComuPostQueryService;
import com.developer.search.query.dto.SearchResultDTO;
import com.developer.search.query.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/comu")
@RestController
@Slf4j
public class ComuPostQueryController {

    private final ComuPostQueryService comuPostQueryService;
    private final SearchService searchService;
    
    @GetMapping("/postlist")
    public ResponseEntity<List<ComuPostResponseDTO>> selectAllComuPost(@RequestParam(defaultValue = "1") Integer page) {
        List<ComuPostResponseDTO> comuList = comuPostQueryService.selectAllComuPost(page);

        return ResponseEntity.ok(comuList);
    }

    @GetMapping("/postlist/{comuPostCode}")
    public ResponseEntity<ComuPostResponseDTO> selectComuPostByCode(@PathVariable(name = "comuPostCode") Long comuPostCode) {
        ComuPostResponseDTO comuPostResponseDTO = comuPostQueryService.selectComuPostByCode(comuPostCode);

        return ResponseEntity.ok(comuPostResponseDTO);
    }

    // 커뮤니티 게시판 내에서 검색하기
    @GetMapping("/search")
    public ResponseEntity<List<SearchResultDTO>> searchComuPost(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page)
    {
        List<SearchResultDTO> comuPostSearchResultDTO = searchService.search("comu", keyword, page);
        return ResponseEntity.ok(comuPostSearchResultDTO);
    }

}
