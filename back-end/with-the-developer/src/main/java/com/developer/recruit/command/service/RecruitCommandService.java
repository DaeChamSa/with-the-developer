package com.developer.recruit.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.recruit.command.dto.RecruitApplyDTO;
import com.developer.recruit.command.entity.Recruit;
import com.developer.recruit.command.repository.RecruitRepository;
import com.developer.user.command.dto.SessionSaveDTO;
import com.developer.user.command.entity.User;
import com.developer.user.command.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitCommandService {

    private final RecruitRepository recruitRepository;
    private final UserRepository userRepository;

    // 로그인 된 사용자 가져오기
    public User getLoggedUser(HttpServletRequest request) {
        // session에 저장된 userCode 가져오기
        SessionSaveDTO sessionSaveDTO = (SessionSaveDTO) request.getSession().getAttribute("user");

        if (sessionSaveDTO == null || sessionSaveDTO.getUserCode() == null) {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }

        Long userCode = sessionSaveDTO.getUserCode();

        return userRepository.findById(userCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNDED_USER));
    }

    // 채용공고 등록 신청하기
    @Transactional
    public Long applyRecruit(RecruitApplyDTO newRecruitApplyDTO, User user) {
        Recruit recruit = new Recruit(newRecruitApplyDTO, user);
        recruitRepository.save(recruit);

        return recruit.getRecruitCode();
    }
}