package com.developer.admin.query.mapper;

import com.developer.admin.query.dto.RecruitApplyDetailReadDTO;
import com.developer.admin.query.dto.RecruitApplyListReadDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {

    // 채용공고 등록 신청 내역 목록 조회
    List<RecruitApplyListReadDTO> readRecruitApplyList(int offset);

    // 채용공고 등록 신청 상세 내역 조회
    RecruitApplyDetailReadDTO readRecruitApplyDetailById(@Param("recruitCode") Long recruitCode);
}
