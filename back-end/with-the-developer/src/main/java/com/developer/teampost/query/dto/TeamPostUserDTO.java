package com.developer.teampost.query.dto;

import lombok.Data;

// 팀 모집 게시글 조회 시 포함 될 작성자 정보
@Data
public class TeamPostUserDTO {

    private Long userCode;
    private String userNick;

}
