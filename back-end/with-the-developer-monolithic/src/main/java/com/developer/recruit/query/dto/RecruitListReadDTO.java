package com.developer.recruit.query.dto;

import com.developer.recruit.command.entity.RecruitStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class RecruitListReadDTO {

    private Long recruitCode;

    private String recruitTitle;

    private LocalDateTime recruitStart;

    private LocalDateTime recruitEnd;

    private RecruitStatus recruitStatus;

    private String userId;

    private Long bookmarkCount;

    private List<String> jobTagNames;

    private Long reportCount;
}