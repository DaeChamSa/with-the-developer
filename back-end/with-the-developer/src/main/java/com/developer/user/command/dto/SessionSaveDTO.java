package com.developer.user.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionSaveDTO {

    private Long userCode;
    private String userId;
}
