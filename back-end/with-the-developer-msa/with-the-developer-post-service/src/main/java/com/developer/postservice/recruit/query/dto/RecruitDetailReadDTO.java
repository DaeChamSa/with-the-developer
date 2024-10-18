package com.developer.postservice.recruit.query.dto;

import com.developer.postservice.image.command.entity.Image;
import com.developer.postservice.recruit.command.entity.RecruitStatus;
import com.developer.postservice.recruit.command.entity.RecruitStatus;
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

