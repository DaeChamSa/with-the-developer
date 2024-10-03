package com.developer.comu.post.command.controller;

import com.developer.common.success.SuccessCode;
import com.developer.common.module.PostAndImageService;
import com.developer.comu.post.command.dto.ComuPostCreateDTO;
import com.developer.comu.post.command.dto.ComuPostUpdateDTO;
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
import java.text.ParseException;
import java.util.List;

@Tag(name = "community-post", description = "커뮤니티 게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comu")
public class ComuPostController {

    private final PostAndImageService postAndImageService;

    // 커뮤니티 게시글 등록
    @PostMapping(value = "/regist",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "커뮤니티 게시글 등록", description = "새로운 커뮤니티 게시글을 등록합니다.")
    public ResponseEntity<Void> createComuPost(
            @RequestPart(value = "comuPostCreateDTO") ComuPostCreateDTO comuPostCreateDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException, ParseException {

        String userId = SecurityUtil.getCurrentUserId();

        // 게시글 등록
        Long comuPostCode = postAndImageService.comuPostRegist(comuPostCreateDTO, userId, images);

        URI location = URI.create("/comu-post/" + comuPostCode);

        return ResponseEntity.created(location).build();
    }

    // 커뮤니티 게시글 수정
    @PutMapping(value = "/update",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "커뮤니티 게시글 수정", description = "등록되어 있는 커뮤니티 게시글을 수정합니다.")
    public ResponseEntity<SuccessCode> updateComuPost(
            @RequestPart ComuPostUpdateDTO comuPostUpdateDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images
            ) throws IOException, ParseException {

        String userId = SecurityUtil.getCurrentUserId();

        // 게시글 수정
        postAndImageService.comuPostUpdate(comuPostUpdateDTO, userId, images);

        return ResponseEntity.ok(SuccessCode.COMU_POST_UPDATE_OK);
    }

    // 커뮤니티 게시글 삭제
    @DeleteMapping("/delete/{comuPostCode}")
    @Operation(summary = "커뮤니티 게시글 삭제", description = "등록되어 있는 커뮤니티 게시글을 삭제합니다.")
    public ResponseEntity<SuccessCode> deleteComuPost(@PathVariable Long comuPostCode) {

        String userId = SecurityUtil.getCurrentUserId();

        // 게시글 삭제
        postAndImageService.comuPostDelete(comuPostCode, userId);

        return ResponseEntity.ok(SuccessCode.PROJ_POST_DELETE_OK);
    }
}