package com.developer.project.post.command.application.controller;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.common.SuccessCode;
import com.developer.comu.module.PostAndImageService;
import com.developer.user.command.dto.TokenSaveDTO;
import com.developer.image.command.service.ImageService;
import com.developer.project.post.command.application.dto.ProjPostRequestDTO;
import com.developer.project.post.command.application.service.ProjPostCommandService;
import com.developer.user.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
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
    private final ImageService imageService;
    private final PostAndImageService postAndImageService;

    @PostMapping("/post")
    public ResponseEntity<Void> createProjPost(
            @RequestPart ProjPostRequestDTO projPostRequestDTO,
            @RequestPart MultipartFile[] images
    ) throws IOException {

        Long loginUserCode = SecurityUtil.getCurrentUserCode();

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

        postAndImageService.projPostUpdate(projPostCode, loginUserCode, projPostRequestDTO, images);

        return ResponseEntity.ok(SuccessCode.PROJ_POST_UPDATE_OK);
    }

    @DeleteMapping("/post/{projPostCode}")
    public ResponseEntity<SuccessCode> deleteProjPost(@PathVariable Long projPostCode, HttpServletRequest httpServletRequest) {
        Long loginUserCode = SecurityUtil.getCurrentUserCode();

        projPostCommandService.deleteProjPost(loginUserCode, projPostCode);

        return ResponseEntity.ok(SuccessCode.PROJ_POST_DELETE_OK);
    }

}
