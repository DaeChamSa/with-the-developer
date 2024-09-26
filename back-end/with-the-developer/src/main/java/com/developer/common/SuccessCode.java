package com.developer.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    USER_LOGIN_OK("로그인 성공"),
    USER_LOGOUT_OK("로그아웃 성공"),

    JOB_TAG_CREATE_OK("직무태그 추가 완료"),

    RECRUIT_APPLY_APPR_OK("채용공고 등록 신청 승인 완료"),
    RECRUIT_APPLY_REJECT_OK("채용공고 등록 신청 반려 완료"),
    RECRUIT_COMPLETE_OK("채용공고 마감 완료"),
    RECRUIT_DELETE_OK("채용공고 삭제 완료"),

    PROJ_POST_UPDATE_OK("프로젝트 자랑 게시글 수정 완료"),
    PROJ_POST_DELETE_OK("프로젝트 자랑 게시글 삭제 완료"),
    PROJ_COMMENT_UPDATE_OK("프로젝트 자랑 댓글 수정 완료"),
    PROJ_COMMENT_DELETE_OK("프로젝트 자랑 댓글 삭제 완료"),

    REPORT_REASON_CATEGORY_CREATE_OK("신고 사유 카테고리 추가 완료");


    private final String message;
}
