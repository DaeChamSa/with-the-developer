package com.developer.report.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.report.command.entity.Report;
import com.developer.report.command.repository.ReportRepository;
import com.developer.team.post.command.entity.TeamPost;
import com.developer.team.post.command.repository.TeamPostRepository;
import com.developer.user.command.domain.aggregate.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamPostReportHandler implements ReportHandler {

    private final TeamPostRepository teamPostRepository;
    private final ReportRepository reportRepository;

    public TeamPostReportHandler(TeamPostRepository teamPostRepository, ReportRepository reportRepository) {
        this.teamPostRepository = teamPostRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public void checkStatus(Long postCode) {
        TeamPost teamPost = teamPostRepository.findById(postCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        if (teamPost.isDelStatus()) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }
    }

    @Override
    public int getReportedCount(Report report) {
        return Math.toIntExact(reportRepository.countByComuPost(report.getComuPost()));
    }

    @Override
    public void deletePost(Report report) {
        teamPostRepository.delete(report.getTeamPost());
    }

    @Override
    public User findReportedUser(Report report, Long postCode) {
        TeamPost teamPost = teamPostRepository.findById(postCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        report.updateTeamPostCode(teamPost);
        User reportedUser = teamPost.getUser();

        return reportedUser;
    }

    @Override
    public List<Report> getListToBeApproved(Report report) {
        return reportRepository.findByTeamPost(report.getTeamPost());
    }

}
