package com.developer.cartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@AllArgsConstructor
@Getter
public class ImageUploadDTO {
    private String originFileName;
    private String fileName;
    private String fileType;
    private String fileSize;
    private String dirName;
    private Long code;

}
