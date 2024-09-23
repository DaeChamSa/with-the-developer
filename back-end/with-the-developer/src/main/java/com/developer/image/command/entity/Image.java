package com.developer.image.command.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "image")
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imgCode;
    private String originFileName;
    private String fileName;
    private String fileType;
    private String fileSize;

    private Long goodsCode;
    private Long projPostCode;
    private Long recruitCode;
    private Long teamPostCode;
    private Long comuCode;


    public void uploadTeamPostImage(String originFileName, String fileName, String fileType, String fileSize, Long teamPostCode) {
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.teamPostCode = teamPostCode;
    }

    public Image() {

    }
}
