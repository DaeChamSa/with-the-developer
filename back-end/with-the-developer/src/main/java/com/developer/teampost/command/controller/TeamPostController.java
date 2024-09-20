package com.developer.teampost.command.controller;


import com.developer.common.exception.CustomException;
import com.developer.teampost.command.dto.TeamPostDeleteDTO;
import com.developer.teampost.command.dto.TeamPostRegistDTO;
import com.developer.teampost.command.dto.TeamPostUpdateDTO;
import com.developer.teampost.command.service.TeamPostService;
import com.developer.user.command.dto.SessionSaveDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

import static com.developer.common.exception.ErrorCode.NEED_LOGIN;

@RestController
@RequestMapping("/teamPost")
@Slf4j
@RequiredArgsConstructor
public class TeamPostController {

    private final TeamPostService teamPostService;

    // 게시글 등록
    @PostMapping("/regist")
    public ResponseEntity<?> registTeamPost(
            @RequestPart TeamPostRegistDTO teamPostDTO,
            @RequestPart MultipartFile[] images,
            HttpServletRequest httpServletRequest) throws ParseException {

        teamPostDTO.setUserCode(getLoginUserCode(httpServletRequest));

        teamPostService.registTeamPost(teamPostDTO,images);

        return ResponseEntity.ok("팀 모집 게시글 등록 성공");
    }

    // 게시글 수정
    @PostMapping("/update")
    public ResponseEntity<?> updateTeamPost(@RequestBody TeamPostUpdateDTO teamPostDTO, HttpServletRequest httpServletRequest) throws ParseException {

        teamPostDTO.setUserCode(getLoginUserCode(httpServletRequest));
        teamPostService.updateTeamPost(teamPostDTO);
        return ResponseEntity.ok("팀 모집 게시글 수정 성공");
    }

    // 게시글 삭제
    @PostMapping("/delete")
    public ResponseEntity<?> deleteTeamPost(@RequestBody TeamPostDeleteDTO teamPostDTO, HttpServletRequest httpServletRequest) throws ParseException {

        teamPostDTO.setUserCode(getLoginUserCode(httpServletRequest));
        teamPostService.deleteTeamPost(teamPostDTO);
        return ResponseEntity.ok("팀 모집 게시글 삭제 성공");
    }

    // 현재 로그인 중인 유저 코드 반환 메소드
    private Long getLoginUserCode(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        SessionSaveDTO loginUser = (SessionSaveDTO) session.getAttribute("user");
        if(loginUser == null){
            throw new CustomException(NEED_LOGIN);
        }
        return loginUser.getUserCode();
    }

}
