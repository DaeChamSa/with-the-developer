package com.developer.recruit.command.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecruitStatus {
    UPCOMING("모집전"),
    ACTIVE("모집중"),
    COMPLETED("모집완료"),
    DELETE("삭제");

    private final String recruitStatus;
}
