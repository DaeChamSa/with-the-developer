package com.developer.noti.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotiRecruitCreateDTO {

    private Long userCode;
    private Long recruitCode;
}
