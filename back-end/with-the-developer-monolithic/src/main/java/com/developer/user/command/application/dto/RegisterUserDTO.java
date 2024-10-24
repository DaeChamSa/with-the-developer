package com.developer.user.command.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterUserDTO {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다. 형식: example@email.com")
    private String userId;          // 아이디 = email

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String userPw;          // 비밀번호

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String userName;        // 사용자 이름

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    private String userNick;        // 닉네임

    @NotNull(message = "생일은 필수 입력값입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate userBirth;         // 사용자 생일

    @NotBlank(message = "핸드폰번호는 필수 입력값입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}", message = "올바르지 않은 핸드폰번호 형식입니다. 형식: 010-xxxx-xxxx")
    private String userPhone;       // 사용자 폰번호
}
