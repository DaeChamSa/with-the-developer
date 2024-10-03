package com.developer.project.post.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProjPostListResponseDTO {

    private Long projPostCode;
    private String projPostTitle;
    private Long userCode;
    private String userNick;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<String> projTagContents;
}
