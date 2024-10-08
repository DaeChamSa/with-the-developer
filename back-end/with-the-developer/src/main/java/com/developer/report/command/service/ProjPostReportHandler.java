package com.developer.report.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.project.post.command.domain.aggregate.ProjPost;
import com.developer.project.post.command.domain.repository.ProjPostRepository;
import com.developer.report.command.entity.Report;
import com.developer.report.command.repository.ReportRepository;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjPostReportHandler implements ReportHandler {

    private final ProjPostRepository projPostRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ProjPostReportHandler(ProjPostRepository projPostRepository, ReportRepository reportRepository, UserRepository userRepository) {
        this.projPostRepository = projPostRepository;
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
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
    public User findReportedUser(Report report, Long postCode) {
        ProjPost projPost = projPostRepository.findById(postCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        report.updateProjPostCode(projPost);
        User reportedUser = userRepository.findById(projPost.getUserCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return reportedUser;
    }

    @Override
    public List<Report> getListToBeApproved(Report report) {
        return reportRepository.findByProjPost(report.getProjPost());
    }
}
