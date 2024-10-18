package com.developer.userservice.user.command.application.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class SendEmailDTO {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Email(message = "올바르지 않은 이메일 형식입니다. 형식: example@example.com")
    private String userId;
}
