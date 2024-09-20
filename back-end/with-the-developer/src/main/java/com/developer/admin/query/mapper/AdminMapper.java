package com.developer.admin.query.mapper;

import com.developer.admin.query.dto.RecruitListReadDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    // 채용공고 등록 신청 내역 목록 조회
    List<RecruitListReadDTO> readRecruitApplyList();
}
