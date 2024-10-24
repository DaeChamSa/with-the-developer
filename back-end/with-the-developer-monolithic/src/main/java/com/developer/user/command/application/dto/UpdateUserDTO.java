package com.developer.user.command.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserDTO {

    @Size(min = 8, max = 16, message = "비밀번호는 최소 8글자 이상")
    private String userPw;          // 비밀번호

    private String userName;        // 사용자 이름

    private String userNick;        // 닉네임

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate userBirth;       // 사용자 생일

    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "휴대폰 번호 형식이 올바르지 않습니다. 형식: 010-0000-0000")
    private String userPhone;       // 사용자 폰번호
}
