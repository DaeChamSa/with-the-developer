package com.developer.team.comment.command.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamCmtRegistDTO {

    private String teamCmt;
    private Long teamPostCode;
    private Long userCode;
}
