package com.developer.msg.command.application.dto;

import com.developer.msg.command.domain.aggregate.ReqMsg;
import lombok.Data;

@Data
public class MessageRequestDTO {

    private Long recipientUserCode;

    private String msgContent;

    public ReqMsg toEntity() {
        return ReqMsg.builder()
                .msgContent(msgContent)
                .build();
    }
}
