package com.developer.project.post.query.dto;

import com.developer.image.command.entity.Image;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProjPostResponseDTO {

    private Long projPostCode;
    private String projPostTitle;
    private String projPostContent;
    private String projUrl;
    private Long userCode;
    private String userNick;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<String> projTagContents;
    private List<Image> images;
}
