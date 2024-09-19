package com.developer.user.command.dto;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class UpdateUserDTO {

    private String userPw;          // 비밀번호

    private String userEmail;       // 사용자 이메일

    private String userName;        // 사용자 이름

    private String userNick;        // 닉네임

    private String userBirth;       // 사용자 생일

    private String userPhone;       // 사용자 폰번호

    public Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }
}
