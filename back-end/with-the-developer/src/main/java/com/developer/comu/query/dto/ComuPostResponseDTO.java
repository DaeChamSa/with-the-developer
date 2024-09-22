package com.developer.comu.query.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComuPostResponseDTO {

    private Long comuPostCode;
    private String comuPostSubject;
    private String comuPostContent;
    private LocalDateTime comuCreateDate;
    private LocalDateTime comuUpdateDate;
    private UserDTO userCode;


}
