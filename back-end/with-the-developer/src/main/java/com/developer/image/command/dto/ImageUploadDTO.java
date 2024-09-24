package com.developer.image.command.dto;

import lombok.*;


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
