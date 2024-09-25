package com.developer.project.comment.query.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProjCmtResponseDTO {

    private Long projCmtCode;
    private String projCmtContent;
    private Long projPostCode;
    private Long userCode;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
