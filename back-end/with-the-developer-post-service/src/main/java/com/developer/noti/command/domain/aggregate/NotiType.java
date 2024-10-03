package com.developer.noti.command.domain.aggregate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotiType {

    NOTI_TYPE_COMMENT("새로운 댓글이 달렸습니다."),
    NOTI_TYPE_MESSAGE("새로운 쪽지가 있습니다."),
    NOTI_TYPE_BEST("인기 게시글로 등록되었습니다."),
    NOTI_TYPE_REPORT("게시글이 삭제 처리되었습니다."),
    NOTI_TYPE_ACCEPT("채용 공고가 승인되었습니다."),
    NOTI_TYPE_REJECT("채용 공고가 반려되었습니다.");

    private final String type;
}
