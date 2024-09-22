package com.developer.project.command.application.dto;

import com.developer.project.command.domain.aggregate.ProjPost;
import lombok.*;

@Getter
public class ProjPostRequestDTO {

    private String projPostTitle;
    private String projPostContent;
    private String projUrl;

    public ProjPost toEntity() {
        return ProjPost.builder()
                .projPostTitle(projPostTitle)
                .projPostContent(projPostContent)
                .projUrl(projUrl)
                .build();
    }
}
