package com.developer.comu.command.controller;

import com.developer.common.SuccessCode;
import com.developer.comu.command.dto.ComuPostCreateDTO;
import com.developer.comu.command.dto.ComuPostUpdateDTO;
import com.developer.comu.command.service.ComuPostService;
import com.developer.user.security.SecurityUtil;
import com.developer.image.command.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comu")
public class ComuPostController {

    private final ComuPostService comuPostService;
    private final ImageService imageService;

    // 커뮤니티 게시글 등록
    @PostMapping("/regist")
    public ResponseEntity<Void> createComuPost(
            @RequestPart ComuPostCreateDTO comuPostCreateDTO,
            @RequestPart MultipartFile[] images
    ) throws IOException {
        String userId = SecurityUtil.getCurrentUserId();

        Long comuPostCode = comuPostService.createComuPost(comuPostCreateDTO, userId);

        // 이미지가 있다면 이미지 서비스 호출
        if(images != null && images.length > 1) {
            imageService.upload(images, "comuPost", comuPostCode);
        }

        URI location = URI.create("/comu-post/" + comuPostCode);

        return ResponseEntity.created(location).build();
    }

    // 커뮤니티 게시글 수정
    @PutMapping("/update")
    public ResponseEntity<SuccessCode> updateComuPost(
            @RequestPart ComuPostUpdateDTO comuPostUpdateDTO,
            @RequestPart MultipartFile[] images
    ) throws IOException {
        String userId = SecurityUtil.getCurrentUserId();

        imageService.updateImage(images,"comuPost", comuPostUpdateDTO.getComuCode());

        comuPostService.updateComuPost(comuPostUpdateDTO, userId);

        return ResponseEntity.ok(SuccessCode.COMU_POST_UPDATE_OK);
    }

    // 커뮤니티 게시글 삭제
    @DeleteMapping("/delete/{comuPostCode}")
    public ResponseEntity<SuccessCode> deleteComuPost(@PathVariable Long comuPostCode) {
        String userId = SecurityUtil.getCurrentUserId();

        imageService.deleteImage("comuPost", comuPostCode);

        comuPostService.deleteComuPost(comuPostCode, userId);

        return ResponseEntity.ok(SuccessCode.PROJ_POST_DELETE_OK);
    }
}
