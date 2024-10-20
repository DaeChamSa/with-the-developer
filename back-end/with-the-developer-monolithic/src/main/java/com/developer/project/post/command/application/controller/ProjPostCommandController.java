package com.developer.project.post.command.application.controller;

import com.developer.common.module.PostAndImageService;
import com.developer.common.success.SuccessCode;
import com.developer.project.post.command.application.dto.ProjPostRequestDTO;
import com.developer.search.query.dto.SearchResultDTO;
import com.developer.search.query.service.SearchService;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Tag(name = "project-post", description = "프로젝트 자랑 게시글 API")
@RequiredArgsConstructor
@RequestMapping("/proj")
@RestController
public class ProjPostCommandController {

    private final PostAndImageService postAndImageService;

    @PostMapping(value = "/post",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "프로젝트 자랑 게시글 등록", description = "새로운 프로젝트 자랑 게시글을 등록합니다.")
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
    @Operation(summary = "프로젝트 자랑 게시글 수정", description = "등록되어 있는 프로젝트 자랑 게시글을 수정합니다.")
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
    @Operation(summary = "프로젝트 자랑 게시글 삭제", description = "등록되어 있는 프로젝트 자랑 게시글을 삭제합니다.")
    public ResponseEntity<SuccessCode> deleteProjPost(@PathVariable(value = "projPostCode") Long projPostCode) {
        Long loginUserCode = SecurityUtil.getCurrentUserCode();

        // 게시글 삭제
        postAndImageService.projPostDelete(projPostCode, loginUserCode);

        return ResponseEntity.ok(SuccessCode.PROJ_POST_DELETE_OK);
    }
}
