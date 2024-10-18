package com.developer.report.command.dto;

import com.developer.report.command.entity.Report;
import com.developer.user.command.domain.aggregate.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportCreateResultDTO {
    private Report report;
    private User reportedUser;

    public ReportCreateResultDTO(Report report, User reportedUser) {
        this.report = report;
        this.reportedUser = reportedUser;
    }
}