package com.developer.comu.post.command.controller;

import com.developer.comu.post.command.dto.ComuPostCreateDTO;
import com.developer.comu.post.command.dto.ComuPostUpdateDTO;
import com.developer.comu.post.command.service.ComuPostService;
import com.developer.image.command.service.ImageService;

import com.developer.user.command.dto.SessionSaveDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comu-post")
public class ComuPostController {

    private final ComuPostService comuPostService;
    private final ImageService imageService;

    // 커뮤니티 게시글 등록
    @PostMapping("/regist")
    public ResponseEntity<Void> createComuPost(
            @RequestPart ComuPostCreateDTO comuPostCreateDTO,
            @RequestPart MultipartFile[] images,
            HttpServletRequest httpServletRequest) throws IOException {

        SessionSaveDTO sessionSaveDTO = (SessionSaveDTO) httpServletRequest.getSession().getAttribute("user");
        String userId = sessionSaveDTO.getUserId();

        Long comuPostCode = comuPostService.createComuPost(comuPostCreateDTO, userId);

        // 이미지가 있다면 이미지 서비스 호출
        if(images != null && images.length > 0) {
            imageService.upload(images, "comuPost", comuPostCode);
        }

        URI location = URI.create("/comu-post/" + comuPostCode);

        return ResponseEntity.created(location).build();
    }


    // 커뮤니티 게시글 수정
    @PutMapping("/update")
    public ResponseEntity<Void> updateComuPost(
            @RequestPart ComuPostUpdateDTO comuPostUpdateDTO,
            @RequestPart MultipartFile[] images,
            HttpServletRequest httpServletRequest) throws IOException {
        SessionSaveDTO sessionSaveDTO = (SessionSaveDTO) httpServletRequest.getSession().getAttribute("user");
        String userId = sessionSaveDTO.getUserId();

        imageService.updateImage(images,"comuPost", comuPostUpdateDTO.getComuCode());

        comuPostService.updateComuPost(comuPostUpdateDTO, userId);

        return ResponseEntity.noContent().build();
    }

    // 커뮤니티 게시글 삭제
    @DeleteMapping("/delete/{comuPostCode}")
    public ResponseEntity<Void> deleteComuPost(@PathVariable Long comuPostCode, HttpServletRequest request) {
        SessionSaveDTO sessionSaveDTO = (SessionSaveDTO) request.getSession().getAttribute("user");
        String userId = sessionSaveDTO.getUserId();

        imageService.deleteImage("comuPost", comuPostCode);

        comuPostService.deleteComuPost(comuPostCode, userId);

        return ResponseEntity.noContent().build();
    }


}
