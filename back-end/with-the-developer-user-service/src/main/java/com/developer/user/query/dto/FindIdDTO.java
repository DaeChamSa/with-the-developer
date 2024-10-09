package com.developer.user.query.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FindIdDTO {

    @NotNull(message = "인증코드는 필수 입력값입니다.")
    private String code;
}
