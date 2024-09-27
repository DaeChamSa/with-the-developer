package com.developer.team.post.command.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

// 팀 모집 게시글 삭제에 필요한 데이터 DTO
@Data
@AllArgsConstructor
public class TeamPostDeleteDTO {

    private Long teamPostCode; // 삭제할 게시글 번호
    private Long userCode; // 현재 로그인 중인 유저 코드
}
