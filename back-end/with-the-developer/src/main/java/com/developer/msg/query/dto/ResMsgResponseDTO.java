package com.developer.msg.query.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResMsgResponseDTO {

    private Long msgCode;
    private String msgContent;
    private LocalDateTime createdDate;
    private Boolean isRead;
    private Long userCode;
}
