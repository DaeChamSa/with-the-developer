package com.developer.recruit.query.mapper;

import com.developer.recruit.query.dto.RecruitDetailReadDTO;
import com.developer.recruit.query.dto.RecruitListReadDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecruitMapper {
    // 등록된 채용공고 목록 조회
    List<RecruitListReadDTO> readRecruitList();

    // 등록된 채용공고 상세 내역 조회
    RecruitDetailReadDTO readRecruitDetailById(@Param("recruitCode") Long recruitCode);
}
