package com.developer.user.query.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class CheckCodeDTO {

    @NotNull(message = "아이디는 필수 입력값입니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다. 형식: example@email.com")
    private String userId;

    @NotNull(message = "인증코드는 필수 입력값입니다.")
    private String code;

    private Date sendDate;


}
