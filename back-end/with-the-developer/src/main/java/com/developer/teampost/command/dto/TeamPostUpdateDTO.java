package com.developer.teampost.command.dto;

import lombok.Data;


@Data
public class TeamPostUpdateDTO {

    private Long teamPostCode; // 수정 할 팀 모집 게시글 코드

    private String teamPostTitle; // 팀 모집 게시글 제목

    private String teamContent; // 팀 모집 게시글 본문

    private String teamPostDeadLine; // 팀 모집 게시글 마감일

    private Long userCode; // 현재 로그인 중인 유저 코드
}
