package com.developer.user.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
public class SessionSaveDTO {

    private Long userCode;
    private String userId;
    private List<GrantedAuthority> authorities;


}
