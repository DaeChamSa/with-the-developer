package com.developer.admin.query.dto;

import com.developer.recruit.command.entity.RecruitApprStatus;
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

    private RecruitApprStatus recruitApprStatus;

    private String userId;
}
