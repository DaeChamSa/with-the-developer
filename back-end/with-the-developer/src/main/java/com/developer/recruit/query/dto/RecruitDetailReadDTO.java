package com.developer.recruit.query.dto;

import com.developer.image.command.entity.Image;
import com.developer.recruit.command.entity.RecruitStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

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

    private List<String> jobTagNames;

    private List<Image> images;
}

