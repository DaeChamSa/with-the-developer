package com.developer.image.command.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageUploadDTO {
    private String originFileName;
    private String fileName;
    private String fileType;
    private String fileSize;

    public ImageUploadDTO() {

    }
    @Builder
    public ImageUploadDTO(String originFileName, String fileName, String fileType, String fileSize) {
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
