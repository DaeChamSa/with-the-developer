package com.developer.msgservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotiMsgCreateDTO {

    private Long msgCode;
    private Long reqUserCode;   // 보낸사람
    private Long resUserCode;   // 받은사람

    public NotiMsgCreateDTO(Long msgCode, Long reqUserCode, Long resUserCode) {
        this.msgCode = msgCode;
        this.reqUserCode = reqUserCode;
        this.resUserCode = resUserCode;
    }
}
