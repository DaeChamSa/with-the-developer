package com.developer.report.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.recruit.command.entity.ApprStatus;
import com.developer.recruit.command.entity.Recruit;
import com.developer.recruit.command.entity.RecruitStatus;
import com.developer.recruit.command.repository.RecruitRepository;
import com.developer.report.command.entity.Report;
import com.developer.report.command.repository.ReportRepository;
import com.developer.user.command.domain.aggregate.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitReportHandler implements ReportHandler {

    private final RecruitRepository recruitRepository;
    private final ReportRepository reportRepository;

    @Override
    public User findReportedUser(Report report, Long postCode) {
        Recruit recruit = recruitRepository.findById(postCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        report.updateRecruitCode(recruit);
        User reportedUser = recruit.getUser();

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
