package com.developer.postservice.report.command.service;

import com.developer.postservice.dto.ResponseUserDTO;
import com.developer.postservice.report.command.entity.Report;

import java.util.List;

public interface ReportHandler {

    void checkStatus(Long postCode);
    int getReportedCount(Report report);
    void deletePost(Report report);
    ResponseUserDTO findReportedUser(Report report, Long postCode);
    List<Report> getListToBeApproved(Report report);
}
