package com.developer.teampost.command.controller;


import com.developer.teampost.command.dto.TeamPostRegisterDTO;
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
    public ResponseEntity<?> registTeamPost(@RequestBody  TeamPostRegisterDTO teamPostRegisterDTO, HttpServletRequest httpServletRequest) throws ParseException {

        HttpSession session = httpServletRequest.getSession();
        String userId = (String)session.getAttribute("userId");

        if(userId == null) {
            return ResponseEntity.notFound().build();
        }

        teamPostService.registTeamPost(teamPostRegisterDTO);

        return ResponseEntity.ok("팀 모집 게시글 등록 성공");
    }

}
