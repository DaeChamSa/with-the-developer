package com.developer.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessResponse {

    RECRUIT_APPLY_APPR_OK("채용공고 등록 신청 승인 완료"),
    RECRUIT_APPLY_REJECT_OK("채용공고 등록 신청 반려 완료"),
    RECRUIT_COMPLETE_OK("채용공고 마감 완료"),
    RECRUIT_DELETE_OK("채용공고 삭제 완료");

    private final String message;
}
