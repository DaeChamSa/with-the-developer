package com.developer.notiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotiRecruitCreateDTO {

    private Long userCode;
    private Long recruitCode;
}
