package com.developer.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // == 200 ==
    SUCCESS(HttpStatus.OK, "OK"),

    // == 400 ==
    DUPLICATE_USERID(HttpStatus.BAD_REQUEST, "중복된 사용자 입니다."),
    NOT_MATCH_ROLE(HttpStatus.BAD_REQUEST, "잘못된 권한입니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // == 404 ==
    NOT_FOUNDED_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NOT_FOUNDED_ADMIN(HttpStatus.NOT_FOUND, "없는 관리자 계정입니다."),
    NOT_FOUNDED_CODE(HttpStatus.NOT_FOUND, "이메일 인증코드를 찾을 수 없습니다."),

    // == 500 ==
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다.");

    private final HttpStatus status;
    private final String message;
}
