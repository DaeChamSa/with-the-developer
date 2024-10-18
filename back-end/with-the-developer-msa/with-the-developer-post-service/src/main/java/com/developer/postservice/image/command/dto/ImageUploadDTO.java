package com.developer.postservice.image.command.dto;

import com.developer.postservice.image.command.entity.ImageType;
import com.developer.postservice.image.command.entity.ImageType;
import lombok.*;


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
