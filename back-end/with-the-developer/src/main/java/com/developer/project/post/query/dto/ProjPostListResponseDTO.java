package com.developer.project.post.query.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProjPostListResponseDTO {

    private Long projPostCode;
    private String projPostTitle;
    private Long userCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
