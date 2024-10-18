package com.developer.postservice.report.command.dto;

import com.developer.postservice.report.command.entity.Report;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportCreateResultDTO {
    private Report report;
    private Long reportedUserCode;

    public ReportCreateResultDTO(Report report, Long reportedUserCode) {
        this.report = report;
        this.reportedUserCode = reportedUserCode;
    }
}