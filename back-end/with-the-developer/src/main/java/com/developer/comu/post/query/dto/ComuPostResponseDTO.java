package com.developer.comu.post.query.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComuPostResponseDTO {

    private Long comuCode;
    private String comuSubject;
    private String comuContent;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private ComuPostUserDTO user;
}
