package com.developer.postservice.report.command.service;

import com.developer.postservice.client.UserServiceClient;
import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.dto.ResponseUserDTO;
import com.developer.postservice.team.post.command.entity.TeamPost;
import com.developer.postservice.team.post.command.repository.TeamPostRepository;
import com.developer.postservice.report.command.entity.Report;
import com.developer.postservice.report.command.repository.ReportRepository;
import com.developer.postservice.team.post.command.entity.TeamPost;
import com.developer.postservice.team.post.command.repository.TeamPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamPostReportHandler implements ReportHandler {

    private final TeamPostRepository teamPostRepository;
    private final ReportRepository reportRepository;
    private final UserServiceClient userServiceClient;

    public TeamPostReportHandler(TeamPostRepository teamPostRepository, ReportRepository reportRepository, UserServiceClient userServiceClient) {
        this.teamPostRepository = teamPostRepository;
        this.reportRepository = reportRepository;
        this.userServiceClient = userServiceClient;
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
    public ResponseUserDTO findReportedUser(Report report, Long postCode) {
        TeamPost teamPost = teamPostRepository.findById(postCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        report.updateTeamPostCode(teamPost);
        ResponseUserDTO reportedUser = userServiceClient.findByUserCode(teamPost.getUserCode());

        return reportedUser;
    }

    @Override
    public List<Report> getListToBeApproved(Report report) {
        return reportRepository.findByTeamPost(report.getTeamPost());
    }

}
