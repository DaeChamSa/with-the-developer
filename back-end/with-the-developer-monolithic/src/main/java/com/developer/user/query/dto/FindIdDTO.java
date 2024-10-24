package com.developer.user.query.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FindIdDTO {

    // 이름
    @NotNull(message = "이름은 필수 입력값입니다.")
    private String userName;
    // 핸드폰 번호
    @NotNull(message = "핸드폰 번호는 필수 입력값입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}", message = "올바르지 않은 핸드폰번호 형식입니다. 형식: 010-xxxx-xxxx")
    private String userPhone;

}
