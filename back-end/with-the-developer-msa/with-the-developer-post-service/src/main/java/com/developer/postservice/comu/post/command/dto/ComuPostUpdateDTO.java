package com.developer.postservice.comu.post.command.dto;


import lombok.Data;

@Data
public class ComuPostUpdateDTO {

    private Long comuCode;
    private String comuSubject;
    private String comuContent;
}
