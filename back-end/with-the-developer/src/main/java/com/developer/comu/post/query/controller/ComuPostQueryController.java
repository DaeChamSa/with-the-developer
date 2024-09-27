package com.developer.comu.post.query.controller;

import com.developer.comu.post.query.service.ComuPostQueryService;
import com.developer.comu.post.query.dto.ComuPostResponseDTO;
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
    
    @GetMapping("/postlist")
    public ResponseEntity<List<ComuPostResponseDTO>> selectAllComuPost(@RequestParam(defaultValue = "1") Integer page) {
        List<ComuPostResponseDTO> comuList = comuPostQueryService.selectAllComuPost(page);

        return ResponseEntity.ok(comuList);
    }

    @GetMapping("/postlist/{comuPostCode}")
    public ResponseEntity<ComuPostResponseDTO> selectComuPostByCode(@PathVariable Long comuPostCode) {
        ComuPostResponseDTO comuPostResponseDTO = comuPostQueryService.selectComuPostByCode(comuPostCode);

        return ResponseEntity.ok(comuPostResponseDTO);
    }

}
