package com.developer.postservice.report.command.service;

import com.developer.postservice.client.UserServiceClient;
import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.comu.post.command.entity.ComuPost;
import com.developer.postservice.comu.post.command.repository.ComuPostRepository;
import com.developer.postservice.dto.ResponseUserDTO;
import com.developer.postservice.report.command.entity.Report;
import com.developer.postservice.report.command.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComuReportHandler implements ReportHandler {

    private final ComuPostRepository comuPostRepository;
    private final ReportRepository reportRepository;
    private final UserServiceClient userServiceClient;

    public ComuReportHandler(ComuPostRepository comuPostRepository, ReportRepository reportRepository, UserServiceClient userServiceClient) {
        this.comuPostRepository = comuPostRepository;
        this.reportRepository = reportRepository;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public void checkStatus(Long postCode) {
        ComuPost comuPost = comuPostRepository.findById(postCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        if (comuPost.isDelStatus()) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }
    }

    @Override
    public int getReportedCount(Report report) {
        return Math.toIntExact(reportRepository.countByComuPost(report.getComuPost()));
    }

    @Override
    public void deletePost(Report report) {
        comuPostRepository.delete(report.getComuPost());
    }

    @Override
    public ResponseUserDTO findReportedUser(Report report, Long postCode) {
        ComuPost comuPost = comuPostRepository.findById(postCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        report.updateComuCode(comuPost);

        return userServiceClient.findByUserCode(comuPost.getUserCode());
    }

    @Override
    public List<Report> getListToBeApproved(Report report) {
        return reportRepository.findByComuPost(report.getComuPost());
    }
}
