package com.developer.comu.command.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ComuPostDTO {

    private long comuCode;
    private String comuSubject;
    private String comuContent;
//    private LocalDateTime comuCreateDate;
//    private LocalDateTime comuUpdateDate;
//    private boolean comuDelStatus;
//    private User userCode;
}
