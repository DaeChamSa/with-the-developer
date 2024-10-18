package com.developer.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseBannedUserDTO {

    private Long bannedCode;

    private LocalDateTime bannedDate;

    private Long userCode;
}
