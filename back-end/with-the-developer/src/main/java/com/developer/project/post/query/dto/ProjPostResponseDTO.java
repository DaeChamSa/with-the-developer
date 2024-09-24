package com.developer.project.post.query.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProjPostResponseDTO {

    private Long projPostCode;
    private String projPostTitle;
    private String projPostContent;
    private String projUrl;
    private Long userCode;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
