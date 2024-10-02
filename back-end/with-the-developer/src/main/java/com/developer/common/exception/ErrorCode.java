package com.developer.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**
     * == 200 ==
     */
    SUCCESS(HttpStatus.OK, "OK"),


    /**
     * == 400 ==
     */
    // 회원가입 (user)
    DUPLICATE_USERID(HttpStatus.BAD_REQUEST, "중복된 사용자입니다."),
    DUPLICATE_USEREMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    DUPLICATE_USERNICK(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),
    DUPLICATE_USERPHONE(HttpStatus.BAD_REQUEST, "중복된 핸드폰 번호입니다."),

    // 로그인 (user)
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    NEED_LOGIN(HttpStatus.BAD_REQUEST, "로그인이 필요한 서비스입니다."),
    NOT_MATCH_USER_INFO(HttpStatus.NOT_FOUND, "회원정보가 일치하지 않습니다. (Email)"),

    // 토큰 (user)
    NOT_MATCH_TOKEN_DETAIL(HttpStatus.BAD_REQUEST, "토큰의 정보와 아이디가 일치하지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),

    // 결제 (payment)
    PAYMENT_NOT_MATCH_USER(HttpStatus.BAD_REQUEST, "결제정보와 유저정보가 일치하지 않습니다."),

    DUPLICATE_BOOKMARK(HttpStatus.BAD_REQUEST, "중복된 북마크 입니다."),

    NOT_MATCH_ROLE(HttpStatus.BAD_REQUEST, "잘못된 권한입니다."),
    NOT_MATCH_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "이미지 파일만 업로드 가능합니다.(jpg, jpeg, png)"),
    NOT_MATCH_DBTI_ROLE(HttpStatus.BAD_REQUEST, "존재하지 않는 역할군입니다.(BACKEND, FRONTEND, DESIGNER, PM"),

    MISSING_VALUE(HttpStatus.BAD_REQUEST, "해당 필드값은 필수입니다."),
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "유효하지 않은 값입니다."),
    NO_VALID_VALUE(HttpStatus.BAD_REQUEST, "모든 필드가 NULL입니다. NULL이 아닌 유효한 필드가 반드시 하나 존재해야 합니다."),
    NO_VALID_MESSAGE_USER(HttpStatus.BAD_REQUEST, "본인에게 쪽지를 보낼 수 없습니다."),

    // 검색
    INVALID_KEYWORD(HttpStatus.BAD_REQUEST, "두글자 이상 검색어를 입력해주세요."),


    /**
     * == 401 ==
     */
    // 토큰 만료
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),

    /**
     * == 403 ==
     */
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "자신의 게시물이 아닙니다."),
    UNAUTHORIZED_USER_COMMENT(HttpStatus.FORBIDDEN, "자신의 댓글이 아닙니다."),
    UNAUTHORIZED_USER_BOOKMARK(HttpStatus.FORBIDDEN, "자신의 북마크가 아닙니다."),
    UNAUTHORIZED_USER_MESSAGE(HttpStatus.FORBIDDEN, "자신의 쪽지가 아닙니다."),
    BLOCKED_BY_USER(HttpStatus.FORBIDDEN, "해당 회원에게 메시지를 보낼 수 없습니다."),

    /**
     * == 404 ==
     */
    // 사용자 (user)
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NOT_FOUND_ADMIN(HttpStatus.NOT_FOUND, "없는 관리자 계정입니다."),
    NOT_FOUND_CODE(HttpStatus.NOT_FOUND, "이메일 인증코드를 찾을 수 없습니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "해당 리프레쉬 토큰을 찾을 수 없습니다."),

    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "해당 게시물을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),

    DELETED_POST(HttpStatus.NOT_FOUND, "삭제된 게시물입니다."),
    DELETED_COMMENT(HttpStatus.NOT_FOUND, "삭제된 댓글입니다."),

    NOT_FOUND_JOB_TAG(HttpStatus.NOT_FOUND, "해당 직무태그를 찾을 수 없습니다."),
    NOT_FOUND_DBTI(HttpStatus.NOT_FOUND, "해당 DBTI가 존재하지 않습니다."),
    NOT_FOUND_REPORT_REASON_CATEGORY(HttpStatus.NOT_FOUND, "해당 신고 사유 카테고리를 찾을 수 없습니다."),
    NOT_FOUND_REPORT(HttpStatus.NOT_FOUND, "해당 신고를 찾을 수 없습니다."),
    NOT_FOUND_BOOKMARK(HttpStatus.NOT_FOUND,"해당 북마크가 존재하지 않습니다."),

    NOT_FOUND_POST_TYPE(HttpStatus.NOT_FOUND, "게시글 타입이 존재하지 않습니다."),

    NOT_FOUND_NOTI(HttpStatus.NOT_FOUND, "해당 알림을 찾을 수 없습니다."),

    NOT_FOUND_MESSAGE(HttpStatus.NOT_FOUND, "해당 쪽지를 찾을 수 없습니다."),

    NOT_FOUND_BLOCK(HttpStatus.NOT_FOUND, "차단 내역을 찾을 수 없습니다."),

    // 수식어 (prefix)
    NOT_FOUND_PREFIX(HttpStatus.NOT_FOUND, "수식어가 존재하지 않습니다."),

    // 결제 (payment)
    NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND, "해당하는 결제 내역이 없습니다."),
    NOT_FOUND_PAYMENT_STATUS(HttpStatus.NOT_FOUND, "결제 상태가 잘못되었습니다."),


    // 주문 (order)
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "해당하는 주문 이력이 없습니다."),
    NOT_FOUND_ORDER_LIST(HttpStatus.NOT_FOUND, "주문 이력이 없습니다."),

    // 상품 (goods)
    NOT_FOUND_GOODS(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다."),


    /**
     * == 409 ==
     */
    DUPLICATE_VALUE(HttpStatus.CONFLICT, "이미 존재하는 항목입니다."),
    DUPLICATE_BLOCK(HttpStatus.CONFLICT, "이미 차단된 회원입니다."),

    // 결제
    PAYMENT_ALREADY_PAID(HttpStatus.CONFLICT, "이미 결제가 완료된 상품입니다."),
    PAYMENT_ALREADY_CANCEL(HttpStatus.CONFLICT, "이미 결제가 취소된 상품입니다."),

    // 알림 (user)
    NOTI_ALREADY_ACCEPT(HttpStatus.CONFLICT, "이미 알림이 허용되어 있습니다."),
    NOTI_ALREADY_REJECT(HttpStatus.CONFLICT, "이미 알림이 거부되어 있습니다."),

    /**
     * == 500 ==
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다."),
    INTERNAL_SERVER_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");

    private final HttpStatus status;
    private final String message;
}
