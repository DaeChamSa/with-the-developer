package com.developer.noti.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotiMsgCreateDTO {
    private Long msgCode;
    private Long reqUserCode;   // 보낸사람
    private Long resUserCode;   // 받은사람
}
