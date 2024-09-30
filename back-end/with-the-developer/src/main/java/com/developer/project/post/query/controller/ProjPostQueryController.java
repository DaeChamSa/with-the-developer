package com.developer.project.post.query.controller;

import com.developer.project.post.query.dto.ProjPostListResponseDTO;
import com.developer.project.post.query.dto.ProjPostResponseDTO;
import com.developer.project.post.query.service.ProjPostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/proj")
@RestController
public class ProjPostQueryController {

    private final ProjPostQueryService projPostQueryService;

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
}
