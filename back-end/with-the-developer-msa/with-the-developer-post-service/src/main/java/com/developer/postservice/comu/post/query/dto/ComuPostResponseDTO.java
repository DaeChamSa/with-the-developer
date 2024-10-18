package com.developer.postservice.comu.post.query.dto;

import com.developer.postservice.image.command.entity.Image;
import com.developer.postservice.image.command.entity.Image;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ComuPostResponseDTO {

    private Long comuCode;
    private String comuSubject;
    private String comuContent;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private ComuPostUserDTO user;
    private List<Image> images;
}
