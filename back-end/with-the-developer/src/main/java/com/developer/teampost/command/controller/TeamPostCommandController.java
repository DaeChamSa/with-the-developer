package com.developer.teampost.command.controller;

import com.developer.image.command.service.ImageService;
import com.developer.teampost.command.dto.TeamPostDeleteDTO;
import com.developer.teampost.command.dto.TeamPostRegistDTO;
import com.developer.teampost.command.dto.TeamPostUpdateDTO;
import com.developer.teampost.command.service.TeamPostCommandService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final TeamPostCommandService teamPostCommandService;
    private final ImageService imageService;

    // 게시글 등록
    @PostMapping("/regist")
    public ResponseEntity<String> registTeamPost(
            @RequestPart TeamPostRegistDTO teamPostDTO,
            @RequestPart MultipartFile[] images
    ) throws ParseException, IOException {

        // 로그인 중인 유저 코드 받아와 DTO에 삽입
        teamPostDTO.setUserCode(SecurityUtil.getCurrentUserCode());

        // 서비스 메소드 호출
        Long createdCode = teamPostCommandService.registTeamPost(teamPostDTO);

        // 이미지도 같이 등록 할 경우 ImageService 호출
        if (images != null && images.length > 1) {
            imageService.upload(images, "teamPost", createdCode);
        }

        // 추후 개발 시 생성된 teampost의 상세 페이지 진입을 위해 URI 작성하여 return
        return ResponseEntity.created(URI.create("teamPost/"+createdCode)).build();
    }

    // 게시글 수정
    @PostMapping("/update")
    public ResponseEntity<String> updateTeamPost(
            @RequestPart TeamPostUpdateDTO teamPostDTO,
            @RequestPart MultipartFile[] images
    ) throws ParseException, IOException {

        // 로그인 중인 유저 코드 받아와 DTO에 삽입
        teamPostDTO.setUserCode(SecurityUtil.getCurrentUserCode());

        teamPostCommandService.updateTeamPost(teamPostDTO);

        if (images != null && images.length > 1) {
            imageService.updateImage(images, "teamPost", teamPostDTO.getUserCode());
        } else {
            imageService.deleteImage("teampost", teamPostDTO.getUserCode());
        }

        return ResponseEntity.ok("팀 모집 게시글 수정 성공");
    }

    // 게시글 삭제
    @PostMapping("/delete")
    public ResponseEntity<String> deleteTeamPost(@RequestBody TeamPostDeleteDTO teamPostDTO) throws ParseException {

        // 로그인 중인 유저 코드 받아와 DTO에 삽입
        teamPostDTO.setUserCode(SecurityUtil.getCurrentUserCode());

        imageService.deleteImage("teamPost", teamPostDTO.getTeamPostCode());

        teamPostCommandService.deleteTeamPost(teamPostDTO);

        return ResponseEntity.ok("팀 모집 게시글 삭제 성공");
    }
}
