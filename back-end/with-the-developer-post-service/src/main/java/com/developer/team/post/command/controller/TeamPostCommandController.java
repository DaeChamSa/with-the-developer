package com.developer.team.post.command.controller;

import com.developer.client.UserServiceClient;
import com.developer.common.module.PostAndImageService;
import com.developer.config.TokenDecoder;
import com.developer.team.post.command.dto.TeamPostDeleteDTO;
import com.developer.team.post.command.dto.TeamPostRegistDTO;
import com.developer.team.post.command.dto.TeamPostUpdateDTO;
import jakarta.validation.Valid;
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
    private final TokenDecoder tokenDecoder;
    private final UserServiceClient userServiceClient;

    // 게시글 등록
    @PostMapping(value = "/post",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registTeamPost(
            @RequestHeader("Authorization") String token,
            @RequestPart(name = "teamPostDTO") @Valid TeamPostRegistDTO teamPostDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws ParseException, IOException {

        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        userServiceClient.userCheck(loginUser);

        // 로그인 중인 유저 코드 받아와 DTO에 삽입
        teamPostDTO.setUserCode(loginUser);

        // 게시글 등록
        Long createdCode = postAndImageService.teamPostRegist(teamPostDTO, images);

        // 추후 개발 시 생성된 teampost의 상세 페이지 진입을 위해 URI 작성하여 return
        return ResponseEntity.created(URI.create("teamPost/"+createdCode)).build();
    }

    // 게시글 수정
    @PutMapping(value = "/post/{teamPostCode}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTeamPost(
            @RequestHeader("Authorization") String token,
            @RequestPart(name = "teamPostDTO") @Valid TeamPostUpdateDTO teamPostDTO,
            @PathVariable(name = "teamPostCode") Long teamPostCode,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws ParseException, IOException {

        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        userServiceClient.userCheck(loginUser);

        // 로그인 중인 유저 코드, 수정할 팀 게시글 코드 받아와 DTO에 삽입
        teamPostDTO.setUserCode(loginUser);
        teamPostDTO.setTeamPostCode(teamPostCode);

        // 게시글 수정
        postAndImageService.teamPostUpdate(teamPostDTO, images);

        return ResponseEntity.ok("팀 모집 게시글 수정 성공");
    }

    // 게시글 삭제
    @DeleteMapping("/post/{teamPostCode}")
    public ResponseEntity<String> deleteTeamPost(
            @RequestHeader("Authorization") String token,
            @PathVariable(name = "teamPostCode") Long teamPostCode
    ) throws ParseException {

        // 로그인 중인 유저 코드 받아와 DTO에 삽입
        TeamPostDeleteDTO teamPostDTO = new TeamPostDeleteDTO(teamPostCode, tokenDecoder.getUserCodeFromToken(token));

        // 게시글 삭제
        postAndImageService.teamPostDelete(teamPostDTO);

        return ResponseEntity.ok("팀 모집 게시글 삭제 성공");
    }
}
