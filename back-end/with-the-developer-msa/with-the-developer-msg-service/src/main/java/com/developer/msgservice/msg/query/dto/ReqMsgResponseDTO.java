package com.developer.msgservice.msg.query.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReqMsgResponseDTO {

    private Long msgCode;
    private String msgContent;
    private LocalDateTime createdDate;
    private Long userCode;
}
