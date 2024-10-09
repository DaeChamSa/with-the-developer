package com.developer.user.command.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SendEmailDTO {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String userId;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바르지 않은 이메일 형식입니다. 형식: example@example.com")
    private String userEmail;
}
