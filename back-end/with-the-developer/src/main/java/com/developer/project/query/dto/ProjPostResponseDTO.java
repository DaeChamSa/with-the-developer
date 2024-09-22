package com.developer.project.query.dto;

import com.developer.project.command.domain.aggregate.ProjPost;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ProjPostResponseDTO {

    private Long projPostCode;
    private String projPostTitle;
    private String projPostContent;
    private String projUrl;
    private Long userCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProjPostResponseDTO from(ProjPost projPost) {
        return new ProjPostResponseDTO(
                projPost.getUserCode(),
                projPost.getProjPostTitle(),
                projPost.getProjPostContent(),
                projPost.getProjUrl(),
                projPost.getUserCode(),
                projPost.getCreatedAt(),
                projPost.getUpdatedAt()
        );
    }
}
