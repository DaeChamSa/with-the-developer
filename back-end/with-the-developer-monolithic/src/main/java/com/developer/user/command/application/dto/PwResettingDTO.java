package com.developer.user.command.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PwResettingDTO {
    
    // 비밀번호 재설정

    @NotNull(message = "아이디는 필수 입력값입니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다. 형식: example@email.com")
    private String userId;

    @NotNull(message = "인증코드는 필수 입력값입니다.")
    private String code;

    @NotNull(message = "재설정 할 비밀번호는 필수 입력값입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 최소 8자리 ~ 최대 16자리로 입력 해주세요.")
    private String userPw;
}
