package com.developer.recruit.query.service;

import com.developer.recruit.command.entity.RecruitStatus;
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
@Transactional
class RecruitQueryServiceTest {

    @Autowired
    private RecruitMapper recruitMapper;

    @Autowired
    private RecruitQueryService recruitQueryService;

    @Test
    @DisplayName("등록된 채용공고의 목록을 조회한다.")
    void readRecruitList() {
        // Given
        // When
        List<com.developer.recruit.query.dto.RecruitListReadDTO> recruitList = recruitQueryService.readRecruitList(1);
        // Then
        assertNotNull(recruitList);
        assertFalse(recruitList.isEmpty());

        assertEquals("기획 및 프론트엔드 개발자 모집", recruitList.get(0).getRecruitTitle());
        assertEquals(LocalDateTime.parse("2024-10-01T09:00:00"), recruitList.get(0).getRecruitStart());
        assertEquals(LocalDateTime.parse("2024-10-31T18:00:00"), recruitList.get(0).getRecruitEnd());
        assertEquals(RecruitStatus.DELETE, recruitList.get(0).getRecruitStatus());
        assertEquals("user02", recruitList.get(0).getUserId());
        assertEquals(List.of("프론트엔드", "기획"), recruitList.get(0).getJobTagNames());
    }

    @Test
    @DisplayName("채용공고 상세 내용을 조회한다.")
    void readRecruitDetailById() {
        // WHEN
        RecruitDetailReadDTO recruitDetailReadDTO = recruitMapper.readRecruitDetailById(1L);

        //Then
        assertEquals("기획 및 프론트엔드 개발자 모집", recruitDetailReadDTO.getRecruitTitle());
        assertEquals("신입 및 경력 기획 및 프론트엔드 개발자를 모집합니다. React 및 Vue.js 경험자 우대.", recruitDetailReadDTO.getRecruitContent());
        assertEquals("http://example.com/recruit", recruitDetailReadDTO.getRecruitUrl());
        System.out.println(recruitDetailReadDTO.getRecruitUrl());
        assertEquals(LocalDateTime.parse("2024-10-01T09:00:00"), recruitDetailReadDTO.getRecruitStart());
        assertEquals(LocalDateTime.parse("2024-10-31T18:00:00"), recruitDetailReadDTO.getRecruitEnd());
        assertEquals(List.of("프론트엔드", "기획"), recruitDetailReadDTO.getJobTagNames());
    }
}