package com.developer.admin.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RecruitApplyListReadDTO {

    private String recruitTitle;

    public LocalDateTime recruitApplyDate;

    private boolean recruitApprStatus;

    private String userId;
}
