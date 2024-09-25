package com.developer.image.command.entity;

import com.developer.image.command.dto.ImageUploadDTO;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "image")
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgCode;
    private String originFileName;
    private String fileName;
    private String fileType;
    private String fileSize;

    private Long goodsCode;
    private Long projPostCode;
    private Long recruitCode;
    private Long teamPostCode;
    private Long comuCode;


    public Image() {

    }

    // 어디에서 온 요청인지에 따라 게시글 코드와 이미지 삽입
    public void setImageByDir(ImageUploadDTO imageUploadDTO) {

        this.originFileName = imageUploadDTO.getOriginFileName();
        this.fileName = imageUploadDTO.getFileName();
        this.fileType = imageUploadDTO.getFileType();
        this.fileSize = imageUploadDTO.getFileSize();

        switch (imageUploadDTO.getDirName()){
            case "teamPost" : this.teamPostCode=imageUploadDTO.getCode(); break;
            case "projPost" : this.projPostCode=imageUploadDTO.getCode(); break;
            case "comuPost" : this.comuCode=imageUploadDTO.getCode(); break;
            case "recruit" : this.recruitCode=imageUploadDTO.getCode(); break;
            case "goods" : this.goodsCode=imageUploadDTO.getCode(); break;
        }
    }

}
