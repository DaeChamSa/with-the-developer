package com.developer.recruit.query.service;

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