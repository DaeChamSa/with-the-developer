package com.developer.recruit.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.recruit.command.dto.RecruitApplyDTO;
import com.developer.recruit.command.entity.Recruit;
import com.developer.recruit.command.entity.RecruitStatus;
import com.developer.recruit.command.repository.RecruitRepository;
import com.developer.user.command.dto.SessionSaveDTO;
import com.developer.user.command.entity.User;
import com.developer.user.command.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitCommandService {

    private final RecruitRepository recruitRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    // 로그인 된 사용자 가져오기
    public SessionSaveDTO getLoggedUser(HttpServletRequest request) {
        // session에 저장된 userCode 가져오기
        SessionSaveDTO sessionSaveDTO = (SessionSaveDTO) request.getSession().getAttribute("user");

        if (sessionSaveDTO == null || sessionSaveDTO.getUserCode() == null) {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }

        return sessionSaveDTO;
    }

    // 채용공고 등록 신청하기
    @Transactional
    public Long applyRecruit(RecruitApplyDTO newRecruitApplyDTO, Long userCode) {
        User user =  userRepository.findById(userCode)
              .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNDED_USER));

        Recruit recruit = new Recruit(newRecruitApplyDTO, user);

        recruitRepository.save(recruit);

        return recruit.getRecruitCode();
    }

    // 채용공고 자동 마감(모집일자가 지나면 상태가 자동으로 COMPLETED으로 변경)
    @Scheduled(cron = "0 * * * * *") // 매분 0초마다 실행된다.
    public void CompleteRecruitAuto() {
        LocalDateTime now = LocalDateTime.now();

        // 모집마감일이 지났지만 상태가 COMPLETED나 DELETE가 아닌 채용공고들
        String jpql = "SELECT r From Recruit r WHERE r.recruitEnd < : now AND r.recruitStatus <> :status1 AND r.recruitStatus <> :status2";
        List<Recruit> recruitsToUpdate = entityManager.createQuery(jpql, Recruit.class)
                .setParameter("now", now)
                .setParameter("status1", RecruitStatus.COMPLETED)
                .setParameter("status2", RecruitStatus.DELETE)
                .getResultList();

        // COMPLETED로 바꿔주기
        for (Recruit recruit : recruitsToUpdate) {
            recruit.updateRecruitStatus();
            recruitRepository.save(recruit);
        }
    }

    // 채용공고 삭제하기
    @Transactional
    public void deleteRecruit(Long recruitCode, Long userCode) throws Exception {

        Recruit recruit = recruitRepository.findById(recruitCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 채용공고가 없습니다."));
        // 로그인 된 회원이 해당 채용공고를 작성한 회원인지 체크

        if (recruit.getUser().getUserCode() == userCode) {
            recruit.deleteRecruit();

            recruitRepository.save(recruit);
        } else {
            throw new CustomException(ErrorCode.NOT_MATCH_USERCODE);
        }
    }
}