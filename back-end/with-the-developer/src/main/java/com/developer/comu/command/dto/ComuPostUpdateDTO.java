package com.developer.comu.command.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ComuPostUpdateDTO {

    private long comuCode;
    private String comuSubject;
    private String comuContent;


}
