package com.developer.teampost.command.controller;

import com.developer.common.module.PostAndImageService;
import com.developer.teampost.command.dto.TeamPostDeleteDTO;
import com.developer.teampost.command.dto.TeamPostRegistDTO;
import com.developer.teampost.command.dto.TeamPostUpdateDTO;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;

@RestController
@RequestMapping("/team")
@Slf4j
@RequiredArgsConstructor
public class TeamPostCommandController {

    private final PostAndImageService postAndImageService;

    // 게시글 등록
    @PostMapping(value = "/regist",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registTeamPost(
            @RequestPart TeamPostRegistDTO teamPostDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws ParseException, IOException {

        // 로그인 중인 유저 코드 받아와 DTO에 삽입
        teamPostDTO.setUserCode(SecurityUtil.getCurrentUserCode());

        // 게시글 등록
        Long createdCode = postAndImageService.teamPostRegist(teamPostDTO, images);

        // 추후 개발 시 생성된 teampost의 상세 페이지 진입을 위해 URI 작성하여 return
        return ResponseEntity.created(URI.create("teamPost/"+createdCode)).build();
    }

    // 게시글 수정
    @PostMapping(value = "/update",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTeamPost(
            @RequestPart TeamPostUpdateDTO teamPostDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws ParseException, IOException {

        // 로그인 중인 유저 코드 받아와 DTO에 삽입
        teamPostDTO.setUserCode(SecurityUtil.getCurrentUserCode());

        // 게시글 수정
        postAndImageService.teamPostUpdate(teamPostDTO, images);

        return ResponseEntity.ok("팀 모집 게시글 수정 성공");
    }

    // 게시글 삭제
    @PostMapping("/delete")
    public ResponseEntity<String> deleteTeamPost(@RequestBody TeamPostDeleteDTO teamPostDTO) throws ParseException {

        // 로그인 중인 유저 코드 받아와 DTO에 삽입
        teamPostDTO.setUserCode(SecurityUtil.getCurrentUserCode());

        // 게시글 삭제
        postAndImageService.teamPostDelete(teamPostDTO);

        return ResponseEntity.ok("팀 모집 게시글 삭제 성공");
    }

}
