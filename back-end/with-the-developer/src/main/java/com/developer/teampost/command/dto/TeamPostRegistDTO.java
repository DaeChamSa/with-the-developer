package com.developer.teampost.command.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class TeamPostRegistDTO {

    private String teamPostTitle; // 팀 모집 게시글 제목

    private String teamContent; // 팀 모집 게시글 본문

    private String teamPostDeadLine; // 팀 모집 게시글 마감일

    private Long userCode; // 로그인 중인 유저 코드

}
