package com.developer.recruit.command.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApprStatus {
    APPROVE // 승인
    , REJECT // 반려
    , WAITING // 확인 전
    , CHECK // 횟수 카운트 완료 ;
}
