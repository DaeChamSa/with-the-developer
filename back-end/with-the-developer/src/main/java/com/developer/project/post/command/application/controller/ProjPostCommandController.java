package com.developer.project.post.command.application.controller;

import com.developer.common.SuccessCode;
import com.developer.common.module.PostAndImageService;
import com.developer.project.post.command.application.dto.ProjPostRequestDTO;
import com.developer.project.post.command.application.service.ProjPostCommandService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/proj")
@RestController
public class ProjPostCommandController {

    private final ProjPostCommandService projPostCommandService;
    private final PostAndImageService postAndImageService;

    @PostMapping("/post")
    public ResponseEntity<Void> createProjPost(
            @RequestPart ProjPostRequestDTO projPostRequestDTO,
            @RequestPart MultipartFile[] images
    ) throws IOException {
        Long loginUserCode = SecurityUtil.getCurrentUserCode();

        // 게시글 등록
        Long projPostCode = postAndImageService.projPostRegist(projPostRequestDTO, loginUserCode, images);

        return ResponseEntity.created(URI.create("/proj/post/" + projPostCode)).build();
    }

    @PutMapping("/post/{projPostCode}")
    public ResponseEntity<SuccessCode> updateProjPost(
            @PathVariable Long projPostCode,
            @RequestPart ProjPostRequestDTO projPostRequestDTO,
            @RequestPart MultipartFile[] images
    ) throws IOException {
        Long loginUserCode = SecurityUtil.getCurrentUserCode();

        // 게시글 수정
        postAndImageService.projPostUpdate(projPostCode, loginUserCode, projPostRequestDTO, images);

        return ResponseEntity.ok(SuccessCode.PROJ_POST_UPDATE_OK);
    }

    @DeleteMapping("/post/{projPostCode}")
    public ResponseEntity<SuccessCode> deleteProjPost(@PathVariable Long projPostCode) {
        Long loginUserCode = SecurityUtil.getCurrentUserCode();

        // 게시글 삭제
        projPostCommandService.deleteProjPost(loginUserCode, projPostCode);

        return ResponseEntity.ok(SuccessCode.PROJ_POST_DELETE_OK);
    }
}
