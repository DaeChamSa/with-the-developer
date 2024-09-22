package com.developer.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    PROJ_POST_UPDATE_OK("프로젝트 자랑 게시글 수정 완료"),
    PROJ_POST_DELETE_OK("프로젝트 자랑 게시글 삭제 완료");

    private final String message;
}
