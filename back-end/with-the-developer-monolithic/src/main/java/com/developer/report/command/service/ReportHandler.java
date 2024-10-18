package com.developer.report.command.service;

import com.developer.report.command.entity.Report;
import com.developer.user.command.domain.aggregate.User;

import java.util.List;

public interface ReportHandler {

    void checkStatus(Long postCode);
    int getReportedCount(Report report);
    void deletePost(Report report);
    User findReportedUser(Report report, Long postCode);
    List<Report> getListToBeApproved(Report report);
}
