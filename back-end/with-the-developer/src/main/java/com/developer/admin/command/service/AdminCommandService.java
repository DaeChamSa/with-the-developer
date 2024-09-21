package com.developer.admin.command.service;

import com.developer.admin.command.dto.AdminRecruitApplyUpdateDTO;
import com.developer.recruit.command.entity.Recruit;
import com.developer.recruit.command.entity.RecruitStatus;
import com.developer.recruit.command.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminCommandService {

    private final RecruitRepository recruitRepository;

    // 채용공고 등록 신청 승인
    @Transactional
    public void updateRecruitApply(Long recruitCode, AdminRecruitApplyUpdateDTO adminRecruitApplyUpdateDTO) {
        Recruit recruit = recruitRepository.findById(recruitCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 채용공고가 없습니다."));

        // 현재 시간
        LocalDateTime now = LocalDateTime.now();

        // 게시 날짜를 승인 시점으로 UPDATE
        adminRecruitApplyUpdateDTO.setRecruitPostDate(now);

        // 모집기간에 따른 상태값 넣어주기(모집전, 모집중, 모집완료)
        if (now.isBefore(recruit.getRecruitStart())) {
            adminRecruitApplyUpdateDTO.setRecruitStatus(RecruitStatus.UPCOMING);
        } else if (now.isAfter(recruit.getRecruitEnd())) {
            adminRecruitApplyUpdateDTO.setRecruitStatus(RecruitStatus.COMPLETED);
        } else {
            adminRecruitApplyUpdateDTO.setRecruitStatus(RecruitStatus.ACTIVE);
        }

        // 승인, 반려 처리는 req로 받은 DTO의 recruitApprStatus 값 그대로 save
        recruit.updateRecruitApply(adminRecruitApplyUpdateDTO);

        recruitRepository.save(recruit);
    }
}