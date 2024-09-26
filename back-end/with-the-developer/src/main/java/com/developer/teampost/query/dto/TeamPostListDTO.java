package com.developer.teampost.query.dto;

import lombok.Data;

// 팀 모집 게시글 전체 목록 조회
@Data
public class TeamPostListDTO {

    private Long teamPostCode;

    private String teamPostTitle;

    private String createdDate;

    private String teamDeadline;

    private TeamPostUserDTO user;
}
