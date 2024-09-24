package com.developer.user.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data

@AllArgsConstructor
public class TokenSaveDTO {

    private Long userCode;
    private String userId;
    private Collection<? extends GrantedAuthority> authorities;


}
