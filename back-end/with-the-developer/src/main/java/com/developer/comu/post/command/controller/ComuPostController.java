package com.developer.comu.post.command.controller;

import com.developer.comu.post.command.dto.ComuPostCreateDTO;
import com.developer.comu.post.command.dto.ComuPostUpdateDTO;
import com.developer.common.success.SuccessCode;
import com.developer.common.module.PostAndImageService;
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
@RequestMapping("/comu")
public class ComuPostController {

    private final PostAndImageService postAndImageService;

    // 커뮤니티 게시글 등록
    @PostMapping("/regist")
    public ResponseEntity<Void> createComuPost(
            @RequestPart ComuPostCreateDTO comuPostCreateDTO,
            @RequestPart MultipartFile[] images
    ) throws IOException, ParseException {

        String userId = SecurityUtil.getCurrentUserId();

        // 게시글 등록
        Long comuPostCode = postAndImageService.comuPostRegist(comuPostCreateDTO, userId, images);

        URI location = URI.create("/comu-post/" + comuPostCode);

        return ResponseEntity.created(location).build();
    }

    // 커뮤니티 게시글 수정
    @PutMapping("/update")
    public ResponseEntity<SuccessCode> updateComuPost(
            @RequestPart ComuPostUpdateDTO comuPostUpdateDTO,
            @RequestPart MultipartFile[] images
    ) throws IOException, ParseException {

        String userId = SecurityUtil.getCurrentUserId();

        // 게시글 수정
        postAndImageService.comuPostUpdate(comuPostUpdateDTO, userId, images);

        return ResponseEntity.ok(SuccessCode.COMU_POST_UPDATE_OK);
    }

    // 커뮤니티 게시글 삭제
    @DeleteMapping("/delete/{comuPostCode}")
    public ResponseEntity<SuccessCode> deleteComuPost(@PathVariable Long comuPostCode) {

        String userId = SecurityUtil.getCurrentUserId();

        // 게시글 삭제
        postAndImageService.comuPostDelete(comuPostCode, userId);

        return ResponseEntity.ok(SuccessCode.PROJ_POST_DELETE_OK);
    }
}
