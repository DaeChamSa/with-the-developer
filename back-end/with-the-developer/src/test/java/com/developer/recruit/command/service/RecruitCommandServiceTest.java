package com.developer.recruit.command.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.recruit.command.dto.RecruitApplyDTO;
import com.developer.recruit.command.entity.Recruit;
import com.developer.recruit.command.entity.RecruitStatus;
import com.developer.recruit.command.repository.RecruitRepository;
import com.developer.recruit.query.dto.RecruitDetailReadDTO;
import com.developer.recruit.query.mapper.RecruitMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecruitCommandServiceTest {

    @Autowired
    private RecruitCommandService recruitCommandService;

    @Autowired
    private RecruitMapper recruitMapper;

    @Autowired
    private RecruitRepository recruitRepository;

    @Test
    @DisplayName(value = "채용공고 등록을 신청할 수 있다.")
    void applyRecruit() {
        // Given
        Long userCode = 3L;

        RecruitApplyDTO recruitApplyDTO = new RecruitApplyDTO();
        recruitApplyDTO.setRecruitTitle("신입 백엔드 개발자 모집합니다.");
        recruitApplyDTO.setRecruitContent("SpringBoot 경험자 우대");
        recruitApplyDTO.setRecruitUrl("www.naver.com");
        recruitApplyDTO.setRecruitStart(LocalDateTime.parse("2024-09-30T09:00:00"));
        recruitApplyDTO.setRecruitEnd(LocalDateTime.parse("2024-10-01T09:00:00"));
        recruitApplyDTO.setJobTagNames(List.of(new String[]{"백엔드"}));

        // When
        recruitCommandService.applyRecruit(recruitApplyDTO, userCode);

        //Then
        RecruitDetailReadDTO recruitDetailReadDTO = recruitMapper.readRecruitDetailById(recruitRepository.findLatestRecruitCode());
        assertEquals("신입 백엔드 개발자 모집합니다.", recruitDetailReadDTO.getRecruitTitle());
        assertEquals("SpringBoot 경험자 우대", recruitDetailReadDTO.getRecruitContent());
        assertEquals("www.naver.com", recruitDetailReadDTO.getRecruitUrl());
        assertEquals(LocalDateTime.parse("2024-09-30T09:00:00"), recruitDetailReadDTO.getRecruitStart());
        assertEquals(LocalDateTime.parse("2024-10-01T09:00:00"), recruitDetailReadDTO.getRecruitEnd());
        assertEquals(List.of(new String[]{"백엔드"}), recruitDetailReadDTO.getJobTagNames());
        recruitRepository.deleteById(recruitRepository.findLatestRecruitCode());
    }

    // 스케줄링 로직 제외 후 테스트
    @Test
    @Transactional
    @DisplayName(value = "")
    void completeRecruitAuto() {
        // Given
        // recruitCode 2인 채용공고의 마감 기한은 지나있다.
        Long recruitCode = 2L;

        // When
        recruitCommandService.CompleteRecruitAuto();

        // Then
        Recruit recruit = recruitRepository.findById(recruitCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        assertEquals(RecruitStatus.COMPLETED, recruit.getRecruitStatus());
    }

    @Test
    @Transactional
    @DisplayName(value = "본인이 등록한 채용공고를 수동으로 마감할 수 있다.")
    void completeRecruitManual() {
        // Given
        Long recruitCode = 2L;
        Long userCode = 3L;

        // When
        recruitCommandService.completeRecruitManual(recruitCode, userCode);

        // Then
        Recruit recruit = recruitRepository.findById(recruitCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        assertEquals(RecruitStatus.COMPLETED, recruit.getRecruitStatus());
    }

    @Test
    @Transactional
    @DisplayName(value = "본인이 등록하지 않은 채용공고를 마감하려고 할 경우 에러가 발생한다.")
    void completeRecruitManualByOther() {
        // Given
        Long recruitCode = 2L;
        Long unAuthorizedUserCode = 4L;

        // When
        CustomException exception = assertThrows(CustomException.class, () -> {
            recruitCommandService.completeRecruitManual(recruitCode, unAuthorizedUserCode);
        });

        // Then
        Recruit recruit = recruitRepository.findById(recruitCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        assertEquals(ErrorCode.UNAUTHORIZED_USER, exception.getErrorCode());
        assertNotEquals(RecruitStatus.COMPLETED, recruit.getRecruitStatus());
    }

    @Test
    @Transactional
    @DisplayName(value = "등록된 채용공고를 삭제할 수 있다.")
    void deleteRecruit() {
        // Given
        Long recruitCode = 28L;
        Long userCode = 3L;

        // When
        recruitCommandService.deleteRecruit(recruitCode, userCode);

        // Then
        Recruit recruit = recruitRepository.findById(recruitCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        assertEquals(RecruitStatus.DELETE, recruit.getRecruitStatus());
    }

    @Test
    @Transactional
    @DisplayName(value = "본인이 등록하지 않은 채용공고를 삭제하려고 할 경우 에러가 발생한다.")
    void deleteRecruitByOther() {
        // Given
        Long unAuthorizedUserCode = 4L;
        Long recruitCode = 29L;

        // When
        CustomException exception = assertThrows(CustomException.class, () -> {
            recruitCommandService.deleteRecruit(recruitCode, unAuthorizedUserCode);
        });

        // Then
        Recruit recruit = recruitRepository.findById(recruitCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        assertEquals(ErrorCode.UNAUTHORIZED_USER, exception.getErrorCode());
        assertNotEquals(RecruitStatus.DELETE, recruit.getRecruitStatus());
    }
}