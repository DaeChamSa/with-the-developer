package com.developer.user.command.application.dto;

import lombok.Data;

@Data
public class PwResettingDTO {
    
    // 비밀번호 재설정
    private String userId;
    private String code;
    private String userPw;
}
