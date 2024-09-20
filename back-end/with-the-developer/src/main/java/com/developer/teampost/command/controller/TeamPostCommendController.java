package com.developer.teampost.command.controller;


import com.developer.common.exception.CustomException;
import com.developer.teampost.command.dto.TeamPostDeleteDTO;
import com.developer.teampost.command.dto.TeamPostRegistDTO;
import com.developer.teampost.command.dto.TeamPostUpdateDTO;
import com.developer.teampost.command.service.TeamPostCommendService;
import com.developer.user.command.dto.SessionSaveDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

import static com.developer.common.exception.ErrorCode.NEED_LOGIN;

@RestController
@RequestMapping("/teamPost")
@Slf4j
@RequiredArgsConstructor
public class TeamPostCommendController {

    private final TeamPostCommendService teamPostCommendService;

    // 게시글 등록
    @PostMapping("/regist")
    public ResponseEntity<?> registTeamPost(
            @RequestPart TeamPostRegistDTO teamPostDTO,
            @RequestPart MultipartFile[] images,
            HttpServletRequest httpServletRequest) throws ParseException {

        // 로그인 중인 유저 코드 받아와 DTO에 삽입
        teamPostDTO.setUserCode(getLoginUserCode(httpServletRequest));

        // 서비스 메소드 호출
        teamPostCommendService.registTeamPost(teamPostDTO,images);

        return ResponseEntity.ok("팀 모집 게시글 등록 성공");
    }

    // 게시글 수정
    @PostMapping("/update")
    public ResponseEntity<?> updateTeamPost(@RequestBody TeamPostUpdateDTO teamPostDTO, HttpServletRequest httpServletRequest) throws ParseException {

        // 로그인 중인 유저 코드 받아와 DTO에 삽입
        teamPostDTO.setUserCode(getLoginUserCode(httpServletRequest));

        teamPostCommendService.updateTeamPost(teamPostDTO);

        return ResponseEntity.ok("팀 모집 게시글 수정 성공");
    }

    // 게시글 삭제
    @PostMapping("/delete")
    public ResponseEntity<?> deleteTeamPost(@RequestBody TeamPostDeleteDTO teamPostDTO, HttpServletRequest httpServletRequest) throws ParseException {

        // 로그인 중인 유저 코드 받아와 DTO에 삽입
        teamPostDTO.setUserCode(getLoginUserCode(httpServletRequest));

        teamPostCommendService.deleteTeamPost(teamPostDTO);

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
            throw new CustomException(NEED_LOGIN);
        }

        // 로그인 중인 유저 코드 리턴
        return loginUser.getUserCode();
    }

}
