package com.developer.teampost.command.controller;


import com.developer.teampost.command.dto.TeamPostDeleteDTO;
import com.developer.teampost.command.dto.TeamPostRegistDTO;
import com.developer.teampost.command.dto.TeamPostUpdateDTO;
import com.developer.teampost.command.service.TeamPostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/teamPost")
@Slf4j
@RequiredArgsConstructor
public class TeamPostController {

    private final TeamPostService teamPostService;

    // 게시글 등록
    @PostMapping("/regist")
    public ResponseEntity<?> registTeamPost(@RequestBody TeamPostRegistDTO teamPostDTO, HttpServletRequest httpServletRequest) throws ParseException {

        HttpSession session = httpServletRequest.getSession();


        teamPostService.registTeamPost(teamPostDTO);

        return ResponseEntity.ok("팀 모집 게시글 등록 성공");
    }

    // 게시글 수정
    @PostMapping("/update")
    public ResponseEntity<?> updateTeamPost(@RequestBody TeamPostUpdateDTO teamPostDTO, HttpServletRequest httpServletRequest) throws ParseException {

        teamPostService.updateTeamPost(teamPostDTO);
        return ResponseEntity.ok("팀 모집 게시글 수정 성공");
    }

    // 게시글 삭제
    @PostMapping("/delete")
    public ResponseEntity<?> deleteTeamPost(@RequestBody TeamPostDeleteDTO teamPostDTO, HttpServletRequest httpServletRequest) throws ParseException {
        teamPostService.deleteTeamPost(teamPostDTO);
        return ResponseEntity.ok("팀 모집 게시글 삭제 성공");
    }

}
