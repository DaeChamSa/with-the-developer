package com.developer.team.comment.command.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamCmtUpdateDTO {

    private String teamCmt;
    private Long userCode;
}
