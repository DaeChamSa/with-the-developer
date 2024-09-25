package com.developer.recruit.command.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApprStatus {
    APPROVE("승인"),
    REJECT("반려"),
    WAITING("대기");

    private final String recruitApprStatus;
}
