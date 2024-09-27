package com.developer.team.comment.query.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseTeamCmtListDTO {

    private String teamCmt;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private String userNick;
}
