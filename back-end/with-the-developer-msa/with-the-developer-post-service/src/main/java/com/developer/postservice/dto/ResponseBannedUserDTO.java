package com.developer.postservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseBannedUserDTO {

    private Long bannedCode;

    private LocalDateTime bannedDate;

    private Long userCode;

    public ResponseBannedUserDTO(Long userCode) {
        this.bannedDate = LocalDateTime.now();
        this.userCode = userCode;
    }
}