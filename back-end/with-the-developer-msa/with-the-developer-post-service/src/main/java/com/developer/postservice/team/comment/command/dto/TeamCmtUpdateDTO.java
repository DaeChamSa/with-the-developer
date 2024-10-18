package com.developer.postservice.team.comment.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamCmtUpdateDTO {

    private String teamCmt;
    private Long userCode;
}
