package com.developer.report.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.comu.command.entity.ComuPost;
import com.developer.comu.command.repository.ComuPostRepository;
import com.developer.project.post.command.domain.aggregate.ProjPost;
import com.developer.project.post.command.domain.repository.ProjPostRepository;
import com.developer.recruit.command.entity.Recruit;
import com.developer.recruit.command.repository.RecruitRepository;
import com.developer.report.command.dto.ReportCreateDTO;
import com.developer.report.command.entity.Report;
import com.developer.report.command.entity.ReportReasonCategory;
import com.developer.report.command.entity.ReportType;
import com.developer.report.command.repository.ReportRepository;
import com.developer.teampost.command.entity.TeamPost;
import com.developer.teampost.command.repository.TeamPostRepository;
import com.developer.user.command.entity.User;
import com.developer.user.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportCommandService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ComuPostRepository comuPostRepository;
    private final RecruitRepository recruitRepository;
    private final ProjPostRepository projPostRepository;
    private final TeamPostRepository teamPostRepository;

    @Transactional
    public Long createRecruitReport(ReportCreateDTO reportCreateDTO, Long userCode, Long postCode, ReportType reportType) {
        User user =  userRepository.findById(userCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        ReportReasonCategory reportReasonCategory = reportCreateDTO.getReportReasonCategory();

        Report report = reportCreateDTO.toEntity();
        report.updateUser(user);
        report.updateRepoReasonCategory(reportReasonCategory);

        switch(reportType) {
            case COMU:
                ComuPost comuPost = comuPostRepository.findById(postCode)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));;
                report.updateComuCode(comuPost);
                break;
            case RECRUIT:
                Recruit recruit = recruitRepository.findById(postCode)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
                report.updateRecruitCode(recruit);
                break;
            case TEAMPOST:
                TeamPost teamPost = teamPostRepository.findById(postCode)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
                report.updateTeamPostCode(teamPost);
                break;
            case PROJPOST:
                ProjPost projPost = projPostRepository.findById(postCode)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
                report.updateProjPostCode(projPost);
                break;
        }

        reportRepository.save(report);
        return report.getRepoCode();
    }
}
