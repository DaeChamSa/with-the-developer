package com.developer.postservice.report.command.service;

import com.developer.postservice.client.UserServiceClient;
import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.dto.ResponseUserDTO;
import com.developer.postservice.project.post.command.domain.aggregate.ProjPost;
import com.developer.postservice.project.post.command.domain.repository.ProjPostRepository;
import com.developer.postservice.report.command.repository.ReportRepository;
import com.developer.postservice.project.post.command.domain.aggregate.ProjPost;
import com.developer.postservice.project.post.command.domain.repository.ProjPostRepository;
import com.developer.postservice.report.command.entity.Report;
import com.developer.postservice.report.command.repository.ReportRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjPostReportHandler implements ReportHandler {

    private final ProjPostRepository projPostRepository;
    private final ReportRepository reportRepository;
    private final UserServiceClient userServiceClient;

    public ProjPostReportHandler(ProjPostRepository projPostRepository, ReportRepository reportRepository, UserServiceClient userServiceClient) {
        this.projPostRepository = projPostRepository;
        this.reportRepository = reportRepository;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public void checkStatus(Long postCode) {
        ProjPost projPost= projPostRepository.findById(postCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        if (projPost.isDelStatus()) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }
    }

    @Override
    public int getReportedCount(Report report) {
        return Math.toIntExact(reportRepository.countByComuPost(report.getComuPost()));
    }

    @Override
    public void deletePost(Report report) {
        projPostRepository.deleteById(report.getProjPost().getProjPostCode());
    }

    @Override
    public ResponseUserDTO findReportedUser(Report report, Long postCode) {
        ProjPost projPost = projPostRepository.findById(postCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        report.updateProjPostCode(projPost);

        ResponseUserDTO reportedUser;
        try {
            reportedUser = userServiceClient.findByUserCode(projPost.getUserCode());
        } catch (FeignException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        return reportedUser;
    }

    @Override
    public List<Report> getListToBeApproved(Report report) {
        return reportRepository.findByProjPost(report.getProjPost());
    }
}
