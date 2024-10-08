package com.developer.report.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.comu.post.command.entity.ComuPost;
import com.developer.comu.post.command.repository.ComuPostRepository;
import com.developer.report.command.entity.Report;
import com.developer.report.command.repository.ReportRepository;
import com.developer.user.command.domain.aggregate.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComuReportHandler implements ReportHandler {

    private final ComuPostRepository comuPostRepository;
    private final ReportRepository reportRepository;

    public ComuReportHandler(ComuPostRepository comuPostRepository, ReportRepository reportRepository) {
        this.comuPostRepository = comuPostRepository;
        this.reportRepository = reportRepository;
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
    public User findReportedUser(Report report, Long postCode) {
        ComuPost comuPost = comuPostRepository.findById(postCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        report.updateComuCode(comuPost);
        User reportedUser = comuPost.getUser();

        return reportedUser;
    }

    @Override
    public List<Report> getListToBeApproved(Report report) {
        return reportRepository.findByComuPost(report.getComuPost());
    }
}
