package com.developer.teampost.command.controller;


import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.teampost.command.dto.TeamPostDeleteDTO;
import com.developer.teampost.command.dto.TeamPostRegistDTO;
import com.developer.teampost.command.dto.TeamPostUpdateDTO;
import com.developer.teampost.command.service.TeamPostCommandService;
import com.developer.user.command.dto.SessionSaveDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.text.ParseException;


@RestController
@RequestMapping("/teamPost")
@Slf4j
@RequiredArgsConstructor
public class TeamPostCommandController {

    private final TeamPostCommandService teamPostCommandService;

    // 게시글 등록
    @PostMapping("/regist")
    public ResponseEntity<String> registTeamPost(
            @RequestPart TeamPostRegistDTO teamPostDTO,
            @RequestPart MultipartFile[] images,
            HttpServletRequest httpServletRequest) throws ParseException {

        // 로그인 중인 유저 코드 받아와 DTO에 삽입
        teamPostDTO.setUserCode(getLoginUserCode(httpServletRequest));

        // 서비스 메소드 호출
        Long createdCode = teamPostCommandService.registTeamPost(teamPostDTO,images);

        // 추후 개발 시 생성된 teampost의 상세 페이지 진입을 위해 URI 작성하여 return
        return ResponseEntity.created(URI.create("teamPost/"+createdCode)).build();
    }

    // 게시글 수정
    @PostMapping("/update")
    public ResponseEntity<String> updateTeamPost(@RequestBody TeamPostUpdateDTO teamPostDTO, HttpServletRequest httpServletRequest) throws ParseException {

        // 로그인 중인 유저 코드 받아와 DTO에 삽입
        teamPostDTO.setUserCode(getLoginUserCode(httpServletRequest));

        teamPostCommandService.updateTeamPost(teamPostDTO);

        return ResponseEntity.ok("팀 모집 게시글 수정 성공");
    }

    // 게시글 삭제
    @PostMapping("/delete")
    public ResponseEntity<String> deleteTeamPost(@RequestBody TeamPostDeleteDTO teamPostDTO, HttpServletRequest httpServletRequest) throws ParseException {

        // 로그인 중인 유저 코드 받아와 DTO에 삽입
        teamPostDTO.setUserCode(getLoginUserCode(httpServletRequest));

        teamPostCommandService.deleteTeamPost(teamPostDTO);

        return ResponseEntity.ok("팀 모집 게시글 삭제 성공");
    }

    // 현재 로그인 중인 유저 코드 반환 메소드
    private Long getLoginUserCode(HttpServletRequest httpServletRequest){

        // request 에서 session 추출
        HttpSession session = httpServletRequest.getSession();

        // 로그인 중인 유저 추출
        SessionSaveDTO loginUser = (SessionSaveDTO) session.getAttribute("user");

        // 로그인 중이 아니라면 예외발생
        if(loginUser == null){
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }

        // 로그인 중인 유저 코드 리턴
        return loginUser.getUserCode();
    }

}
