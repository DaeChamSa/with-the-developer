package com.developer.postservice.admin.query.dto;

import com.developer.postservice.recruit.command.entity.ApprStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportListReadDTO {

    private String repoReasonName;

    private String userId;

    private ApprStatus repoStatus;
}
