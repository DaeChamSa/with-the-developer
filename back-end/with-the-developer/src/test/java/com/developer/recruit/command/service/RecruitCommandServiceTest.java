package com.developer.recruit.command.service;

import com.developer.recruit.command.dto.RecruitApplyDTO;
import com.developer.recruit.command.repository.RecruitRepository;
import com.developer.recruit.query.dto.RecruitDetailReadDTO;
import com.developer.recruit.query.mapper.RecruitMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        RecruitApplyDTO recruitApplyDTO = new RecruitApplyDTO();
        recruitApplyDTO.setRecruitTitle("신입 백엔드 개발자 모집합니다.");
        recruitApplyDTO.setRecruitContent("SpringBoot 경험자 우대");
        recruitApplyDTO.setRecruitUrl("www.naver.com");
        recruitApplyDTO.setRecruitStart(LocalDateTime.parse("2024-09-30T09:00:00"));
        recruitApplyDTO.setRecruitEnd(LocalDateTime.parse("2024-10-01T09:00:00"));
        recruitApplyDTO.setJobTagNames(List.of(new String[]{"백엔드"}));

        // When
        recruitCommandService.applyRecruit(recruitApplyDTO, 3L);

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
}