package com.developer.postservice.admin.query.dto;

import com.developer.postservice.recruit.command.entity.ApprStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ReportDetailReadDTO {

    private Long repoCode;

    private String repoDescription;

    private LocalDateTime repoCreateDate;

    private ApprStatus repoStatus;

    private String userId;
}
