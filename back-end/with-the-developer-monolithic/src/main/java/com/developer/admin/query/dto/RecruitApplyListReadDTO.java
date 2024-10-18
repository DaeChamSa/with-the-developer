package com.developer.admin.query.dto;

import com.developer.recruit.command.entity.ApprStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class RecruitApplyListReadDTO {

    private String recruitTitle;

    public LocalDateTime recruitApplyDate;

    private ApprStatus recruitApprStatus;

    private String userId;

    private List<String> jobTagNames;
}
