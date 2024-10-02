package com.developer.recruit.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.jobTag.command.entity.JobTag;
import com.developer.jobTag.command.entity.RecruitTag;
import com.developer.jobTag.command.repository.JobTagRepository;
import com.developer.recruit.command.dto.RecruitApplyDTO;
import com.developer.recruit.command.entity.Recruit;
import com.developer.recruit.command.entity.RecruitStatus;
import com.developer.recruit.command.repository.RecruitRepository;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.repository.UserRepository;
import jakarta.persistence.EntityManager;
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
    private final JobTagRepository jobTagRepository;
    private final EntityManager entityManager;

    // 채용공고 등록 신청하기
    @Transactional
    public Long applyRecruit(RecruitApplyDTO newRecruitApplyDTO, Long userCode) {
        User user =  userRepository.findById(userCode)
              .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Recruit recruit = newRecruitApplyDTO.toEntity();
        recruit.updateUser(user);

        // req의 jobTagName이 jobTag 엔티티에 존재해야만 등록이 가능하다.
        for(String jobTagName:newRecruitApplyDTO.getJobTagNames()) {
            // req의 jobTagName이 jobTag 엔티티에 존재하지 않을 경우의 예외처리
            JobTag jobTag = jobTagRepository.findByJobTagName(jobTagName)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_JOB_TAG));

            // recruitTag 엔티티(recruit 엔티티와 jobTag 엔티티의 중간 엔티티)에 넣어주기
            RecruitTag recruitTag = new RecruitTag(recruit, jobTag);

            // recruit 엔티티의 recruitTags 리스트에 recruitTag 객체를 추가하고 recruitTag가 recruit 객체를 업데이트 하는 메소드
            recruit.addRecruitTag(recruitTag);
        }

        recruitRepository.save(recruit);
        return recruit.getRecruitCode();
    }

    // 채용공고 자동 마감(모집일자가 지나면 상태가 자동으로 COMPLETED으로 변경)
    @Transactional
    @Scheduled(cron = "0 0 0/1 * * *") // 매 시간 00분(1시간 단위)에 실행된다.
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
            recruit.updateRecruitStatus(RecruitStatus.COMPLETED);
            recruitRepository.save(recruit);
        }
    }

    // 채용공고 수동 마감
    @Transactional
    public void completeRecruitManual(Long recruitCode, Long userCode) {
        Recruit recruit = recruitRepository.findById(recruitCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 로그인 된 회원이 해당 채용공고를 작성한 회원인지 체크
        if (recruit.getUser().getUserCode() == userCode) {
            recruit.updateRecruitStatus(RecruitStatus.COMPLETED);
            recruitRepository.save(recruit);
        } else {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

    // 채용공고 삭제하기
    @Transactional
    public void deleteRecruit(Long recruitCode, Long userCode) {

        Recruit recruit = recruitRepository.findById(recruitCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 로그인 된 회원이 해당 채용공고를 작성한 회원인지 체크
        if (recruit.getUser().getUserCode() == userCode) {
            recruit.updateRecruitStatus(RecruitStatus.DELETE);
            recruitRepository.save(recruit);
        } else {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
    }
}