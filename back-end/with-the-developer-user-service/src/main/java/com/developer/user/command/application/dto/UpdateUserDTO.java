package com.developer.user.command.application.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class UpdateUserDTO {

    private String userPw;          // 비밀번호

    private String userEmail;       // 사용자 이메일

    private String userName;        // 사용자 이름

    private String userNick;        // 닉네임

    private String userBirth;       // 사용자 생일

    private String userPhone;       // 사용자 폰번호

    // 날짜 변환 메서드
    public LocalDate convertStringToDate(String dateString){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, dateFormat);
    }
}
