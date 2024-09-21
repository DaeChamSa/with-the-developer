package com.developer.recruit.command.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
public enum RecruitApprStatus {
    APPROVE("승인"),
    REJECT("반려"),
    WAITING("대기");

    private final String recruitApprStatus;

}
