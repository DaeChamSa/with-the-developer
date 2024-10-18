package com.developer.notiservice.noti.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotiMsgCreateDTO {
    private Long msgCode;
    private Long reqUserCode;   // 보낸사람
    private Long resUserCode;   // 받은사람
}
