package com.developer.teampost.command.dto;

import lombok.Data;

@Data
public class TeamPostDeleteDTO {
    private Long teamPostCode; // 삭제할 게시글 번호
    private Long userCode; // 현재 로그인 중인 유저 코드
}
