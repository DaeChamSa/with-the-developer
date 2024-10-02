package com.developer.project.post.command.application.controller;

import com.developer.common.module.PostAndImageService;
import com.developer.common.success.SuccessCode;
import com.developer.project.post.command.application.dto.ProjPostRequestDTO;
import com.developer.search.query.dto.SearchResultDTO;
import com.developer.search.query.service.SearchService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/proj")
@RestController
public class ProjPostCommandController {

    private final PostAndImageService postAndImageService;

    @PostMapping(value = "/post",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createProjPost(
            @RequestPart(value = "projPostRequestDTO") ProjPostRequestDTO projPostRequestDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException {
        Long loginUserCode = SecurityUtil.getCurrentUserCode();

        // 게시글 등록
        Long projPostCode = postAndImageService.projPostRegist(projPostRequestDTO, loginUserCode, images);

        return ResponseEntity.created(URI.create("/proj/post/" + projPostCode)).build();
    }

    @PutMapping(value = "/post/{projPostCode}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessCode> updateProjPost(
            @PathVariable(name = "projPostCode") Long projPostCode,
            @RequestPart(value = "projPostRequestDTO") ProjPostRequestDTO projPostRequestDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException {
        Long loginUserCode = SecurityUtil.getCurrentUserCode();

        // 게시글 수정
        postAndImageService.projPostUpdate(projPostCode, loginUserCode, projPostRequestDTO, images);

        return ResponseEntity.ok(SuccessCode.PROJ_POST_UPDATE_OK);
    }

    @DeleteMapping("/post/{projPostCode}")
    public ResponseEntity<SuccessCode> deleteProjPost(@PathVariable(value = "projPostCode") Long projPostCode) {
        Long loginUserCode = SecurityUtil.getCurrentUserCode();

        // 게시글 삭제
        postAndImageService.projPostDelete(projPostCode, loginUserCode);

        return ResponseEntity.ok(SuccessCode.PROJ_POST_DELETE_OK);
    }
}
