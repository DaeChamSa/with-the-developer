package com.developer.user.command.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
public class LoginUserDTO {

    @NotNull(message = "아이디는 필수 입력값입니다.")
    private String userId;

    @NotNull(message = "비밀번호는 필수 입력값입니다.")
    private String userPw;

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(userId, userPw);
    }
}
