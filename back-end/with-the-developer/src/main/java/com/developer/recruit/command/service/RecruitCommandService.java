package com.developer.recruit.command.service;

import com.developer.recruit.command.dto.RecruitApplyDTO;
import com.developer.recruit.command.entity.Recruit;
import com.developer.recruit.command.repository.RecruitRepository;
import com.developer.user.command.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitCommandService {

    private final RecruitRepository recruitRepository;

    // 채용공고 등록 신청하기
    @Transactional
    public Long applyRecruit(RecruitApplyDTO newRecruitApplyDTO, User user) {
        Recruit recruit = new Recruit(newRecruitApplyDTO, user);
        recruitRepository.save(recruit);

        return recruit.getRecruitCode();
    }
}


