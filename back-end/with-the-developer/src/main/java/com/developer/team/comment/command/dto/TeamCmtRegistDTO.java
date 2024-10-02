package com.developer.team.comment.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamCmtRegistDTO {

    private String teamCmt;
    private Long teamPostCode;
    private Long userCode;
}
