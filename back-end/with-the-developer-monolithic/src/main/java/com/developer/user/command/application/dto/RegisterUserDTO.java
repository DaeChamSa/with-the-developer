package com.developer.user.command.application.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Date;

@Data
public class RegisterUserDTO {

    private String userId;          // 아이디

    private String userPw;          // 비밀번호

    private String userEmail;       // 사용자 이메일

    private String userName;        // 사용자 이름

    private String userNick;        // 닉네임

    private String userBirth;         // 사용자 생일

    private String userPhone;       // 사용자 폰번호
}
