package com.developer.teampost.query.dto;

import lombok.Data;


// 팀 모집 게시글 상세 정보
@Data
public class TeamPostDTO {

    private Long teamPostCode;

    private String teamPostTitle;

    private String teamContent;

    private String teamCreateDate;

    private String teamUpdateDate;

    private String teamDeadline;

    private Boolean teamDelStatus;

    private UserDTO user;
}
