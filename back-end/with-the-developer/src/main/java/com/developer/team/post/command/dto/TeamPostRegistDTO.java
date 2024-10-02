package com.developer.team.post.command.dto;

import com.developer.team.post.command.entity.TeamPost;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 팀 모집 등록에 필요한 데이터 DTO
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamPostRegistDTO {

    @NotNull(message = "게시글 제목은 필수로 입력되어야 합니다.")
    private String teamPostTitle; // 팀 모집 게시글 제목

    @NotNull(message = "게시글 본문은 필수로 입력되어야 합니다.")
    private String teamContent; // 팀 모집 게시글 본문

    @NotNull(message = "팀 모집 마감일은 필수로 입력되어야 합니다.")
    private String teamPostDeadLine; // 팀 모집 게시글 마감일

    private List<String> jobTagNames;

    private Long userCode; // 로그인 중인 유저 코드


    public TeamPost toEntity() {
        return TeamPost.builder()
                .teamPostTitle(teamPostTitle)
                .teamContent(teamContent)
                .build();
    }
}
