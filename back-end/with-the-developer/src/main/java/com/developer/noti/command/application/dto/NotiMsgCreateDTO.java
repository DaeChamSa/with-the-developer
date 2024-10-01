package com.developer.noti.command.application.dto;

import lombok.Data;

@Data
public class NotiMsgCreateDTO {
    private Long msgCode;
    private Long reqUserCode;
    private Long resUserCode;
}
