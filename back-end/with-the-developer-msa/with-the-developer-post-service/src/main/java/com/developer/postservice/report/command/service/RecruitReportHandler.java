package com.developer.postservice.report.command.service;

import com.developer.postservice.client.UserServiceClient;
import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.dto.ResponseUserDTO;
import com.developer.postservice.recruit.command.repository.RecruitRepository;
import com.developer.postservice.recruit.command.entity.ApprStatus;
import com.developer.postservice.recruit.command.entity.Recruit;
import com.developer.postservice.recruit.command.entity.RecruitStatus;
import com.developer.postservice.recruit.command.repository.RecruitRepository;
import com.developer.postservice.report.command.entity.Report;
import com.developer.postservice.report.command.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitReportHandler implements ReportHandler {

    private final RecruitRepository recruitRepository;
    private final ReportRepository reportRepository;
    private final UserServiceClient userServiceClient;

    @Override
    public ResponseUserDTO findReportedUser(Report report, Long postCode) {
        Recruit recruit = recruitRepository.findById(postCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        report.updateRecruitCode(recruit);
        ResponseUserDTO reportedUser = userServiceClient.findByUserCode(recruit.getUserCode());

        return reportedUser;
    }

    @Override
    public List<Report> getListToBeApproved(Report report) {
        return reportRepository.findByRecruit(report.getRecruit());
    }

    @Override
    public void checkStatus(Long postCode) {
        Recruit recruit = recruitRepository.findById(postCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        if ((recruit.getRecruitStatus() == RecruitStatus.DELETE) || (recruit.getRecruitApprStatus() != ApprStatus.APPROVE)) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }
    }

    @Override
    public int getReportedCount(Report report) {
        return Math.toIntExact(reportRepository.countByComuPost(report.getComuPost()));
    }

    @Override
    public void deletePost(Report report) {
        report.getRecruit().updateRecruitStatus(RecruitStatus.DELETE);
    }
}
