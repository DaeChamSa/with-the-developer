package com.developer.comu.command.controller;

import com.developer.comu.command.dto.ComuPostCreateDTO;
import com.developer.comu.command.dto.ComuPostUpdateDTO;
import com.developer.comu.module.PostAndImageService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comu-post")
public class ComuPostController {

    private final PostAndImageService postAndImageService;

    // 커뮤니티 게시글 등록
    @PostMapping("/regist")
    public ResponseEntity<Void> createComuPost(
            @RequestPart ComuPostCreateDTO comuPostCreateDTO,
            @RequestPart MultipartFile[] images
    ) throws IOException, ParseException {

        String userId = SecurityUtil.getCurrentUserId();

        Long comuPostCode = postAndImageService.comuPostRegist(comuPostCreateDTO, userId, images);

        URI location = URI.create("/comu-post/" + comuPostCode);

        return ResponseEntity.created(location).build();
    }


    // 커뮤니티 게시글 수정
    @PutMapping("/update")
    public ResponseEntity<Void> updateComuPost(
            @RequestPart ComuPostUpdateDTO comuPostUpdateDTO,
            @RequestPart MultipartFile[] images
            ) throws IOException, ParseException {

        String userId = SecurityUtil.getCurrentUserId();

        postAndImageService.comuPostUpdate(comuPostUpdateDTO, userId, images);

        return ResponseEntity.noContent().build();
    }

    // 커뮤니티 게시글 삭제
    @DeleteMapping("/delete/{comuPostCode}")
    public ResponseEntity<Void> deleteComuPost(@PathVariable Long comuPostCode) {

        String userId = SecurityUtil.getCurrentUserId();

        postAndImageService.comuPostDelete(comuPostCode, userId);

        return ResponseEntity.noContent().build();
    }


}
