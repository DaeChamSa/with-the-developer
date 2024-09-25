package com.developer.comu.command.dto;

import com.developer.comu.command.entity.ComuPost;
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
