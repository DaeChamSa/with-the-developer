package com.developer.comu.post.command.dto;

import com.developer.comu.post.command.entity.ComuPost;
import lombok.Data;

@Data
public class ComuPostCreateDTO {

    private String comuSubject;
    private String comuContent;

    public ComuPost toEntity() {
        return ComuPost.builder()
                .comuSubject(comuSubject)
                .comuContent(comuContent)
                .build();
    }
}
