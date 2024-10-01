package com.developer.user.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
public class TokenSaveDTO {    // 정보를 빼와서 UsernamePassword에 넣기 위함

    private Long userCode;
    private String userId;
    private Collection<? extends GrantedAuthority> authorities;
    private String accessToken;     // 액세스 토큰

}
