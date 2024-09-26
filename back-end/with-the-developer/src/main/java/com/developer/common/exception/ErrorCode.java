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
    DUPLICATE_USERID(HttpStatus.BAD_REQUEST, "중복된 사용자입니다."),
    DUPLICATE_USEREMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    DUPLICATE_USERNICK(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),
    DUPLICATE_USERPHONE(HttpStatus.BAD_REQUEST, "중복된 핸드폰 번호입니다."),

    NOT_MATCH_ROLE(HttpStatus.BAD_REQUEST, "잘못된 권한입니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    NEED_LOGIN(HttpStatus.BAD_REQUEST, "로그인이 필요한 서비스입니다."),
    NOT_MATCH_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "이미지 파일만 업로드 가능합니다.(jpg, jpeg, png)"),

    MISSING_VALUE(HttpStatus.BAD_REQUEST, "해당 필드값은 필수입니다."),
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "유효하지 않은 값입니다."),
    // == 403 ==
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "자신의 게시물이 아닙니다."),
    UNAUTHORIZED_USER_COMMENT(HttpStatus.FORBIDDEN, "자신의 댓글이 아닙니다."),

    // == 404 ==
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NOT_FOUND_ADMIN(HttpStatus.NOT_FOUND, "없는 관리자 계정입니다."),
    NOT_FOUND_CODE(HttpStatus.NOT_FOUND, "이메일 인증코드를 찾을 수 없습니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "해당 게시물을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    NOT_FOUND_JOB_TAG(HttpStatus.NOT_FOUND, "해당 직무태그를 찾을 수 없습니다."),

    // == 409 ==
    DUPLICATE_VALUE(HttpStatus.CONFLICT, "이미 존재하는 값입니다."),

    // == 500 ==
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다."),
    INTERNAL_SERVER_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");

    private final HttpStatus status;
    private final String message;
}
