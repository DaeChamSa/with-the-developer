package com.developer.user.query.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CheckCodeDTO {

    private String userId;
    private String userEmail;
    private String code;
    private Date sendDate;


}
