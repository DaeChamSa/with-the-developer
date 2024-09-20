package com.developer.teampost.command.dto;

import lombok.Data;

@Data
public class TeamPostDTO {

    private String teamPostTitle; // 팀 모집 게시글 제목

    private String teamContent; // 팀 모집 게시글 본문

    private String teamPostDeadLine; // 팀 모집 게시글 마감일

    private Long userCode; // 게시글 작성자 고유 번호
}
