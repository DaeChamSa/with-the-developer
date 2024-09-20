package com.developer.comu.command.controller;

import com.developer.comu.command.dto.ComuPostDTO;
import com.developer.comu.command.entity.ComuPost;
import com.developer.comu.command.service.ComuPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comu-post")
public class ComuPostController {

    private final ComuPostService comuPostService;

    // 게시글 등록
    @PostMapping("/regist")
    public ResponseEntity<Void> createComuPost(
            @RequestBody ComuPostDTO comuPostDTO) {
        ComuPost savedPost =comuPostService.createComuPost(comuPostDTO);

        URI location = URI.create("/comu-post/" + savedPost.getComuCode());

        return ResponseEntity.created(location).build();
    }
}
