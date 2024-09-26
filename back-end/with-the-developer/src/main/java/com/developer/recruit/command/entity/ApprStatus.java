package com.developer.recruit.command.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApprStatus {
    APPROVE, REJECT, WAITING;
}
