package com.developer.noti.query.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AllNotiDTO {
    private Long notiCode;
    private String notiTitle;
    private boolean notiRead;
    private String notiUrl;
    private LocalDateTime notiCreateDate;
    private boolean notiDelStatus;
}
