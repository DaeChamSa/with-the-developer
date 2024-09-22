package com.developer.recruit.query.dto;

import com.developer.recruit.command.entity.RecruitStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RecruitDetailReadDTO {

    private String recruitTitle;

    private String recruitContent;

    private String recruitUrl;

    private LocalDateTime recruitStart;

    private LocalDateTime recruitEnd;

    private LocalDateTime recruitPostDate;

    private RecruitStatus recruitStatus;

    private String userId;
}

