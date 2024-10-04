package com.developer.image.command.dto;

import com.developer.image.command.entity.ImageType;
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
    private ImageType dirName;
    private Long code;

}
