package com.developer.common.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // 사용자 (user)
    USER_LOGIN_OK("로그인 성공"),
    USER_LOGOUT_OK("로그아웃 성공"),
    PW_RESETTING_OK("비밀번호 재설정 완료"),

    DBTI_CREATE_OK("성향 추가 완료"),
    DBTI_DELETE_OK("성향 삭제 완료"),

    JOB_TAG_CREATE_OK("직무태그 추가 완료"),
    JOB_TAG_DELETE_OK("직무태그 삭제 완료"),

    PREFIX_CREATE_OK("수식어 생성 완료"),

    RECRUIT_APPLY_APPR_OK("채용공고 등록 신청 승인 완료"),
    RECRUIT_APPLY_REJECT_OK("채용공고 등록 신청 반려 완료"),
    RECRUIT_COMPLETE_OK("채용공고 마감 완료"),
    RECRUIT_DELETE_OK("채용공고 삭제 완료"),

    COMU_POST_UPDATE_OK("커뮤니티 게시글 수정 완료"),
    COMU_POST_DELETE_OK("커뮤니티 게시글 삭제 완료"),

    PROJ_POST_UPDATE_OK("프로젝트 자랑 게시글 수정 완료"),
    PROJ_POST_DELETE_OK("프로젝트 자랑 게시글 삭제 완료"),
    PROJ_COMMENT_UPDATE_OK("프로젝트 자랑 댓글 수정 완료"),
    PROJ_COMMENT_DELETE_OK("프로젝트 자랑 댓글 삭제 완료"),

    TEAM_COMMENT_CREATE_OK("팀 모집 댓글 등록 완료"),
    TEAM_COMMENT_UPDATE_OK("팀 모집 댓글 수정 완료"),
    TEAM_COMMENT_DELETE_OK("팀 모집 댓글 삭제 완료"),

    NOT_FOUND_NOTI_OK("알림이 없습니다."),
    NOTI_READ_OK("알림을 읽었습니다."),
    NOTI_DELETE_OK("알림을 삭제했습니다."),

    BOOKMARK_CREATE_OK("북마크 등록 완료"),
    BOOKMARK_DELETE_OK("북마크 삭제 완료"),

    REPORT_REASON_CATEGORY_CREATE_OK("신고 사유 카테고리 추가 완료"),
    REPORT_REASON_CATEGORY_DELETE_OK("신고 사유 카테고리 삭제 완료"),
    REPORT_HANDLE_OK("신고 처리 완료"),

    MESSAGE_UPDATE_OK("쪽지 읽음 여부 변경 완료"),
    MESSAGE_DELETE_OK("쪽지 삭제 완료"),

    BLOCK_OK("차단 성공"),
    UNBLOCK_OK("차단 해제 성공"),

    // 주문 (order)
    ORDER_CANCEL_OK("주문 취소 완료"),

    // 토큰
    ACCESS_TOKEN_REISSUE_OK("AccessToken 재발급 완료"),

    // 알림
    NOTI_ACCEPT_OK("알림 허용 완료"),
    NOTI_REJECT_OK("알림 거부 완료"),;

    private final String message;
}
