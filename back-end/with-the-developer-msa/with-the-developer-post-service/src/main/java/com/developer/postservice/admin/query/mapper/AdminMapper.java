package com.developer.postservice.admin.query.mapper;

import com.developer.postservice.admin.query.dto.RecruitApplyDetailReadDTO;
import com.developer.postservice.admin.query.dto.RecruitApplyListReadDTO;
import com.developer.postservice.admin.query.dto.ReportDetailReadDTO;
import com.developer.postservice.admin.query.dto.ReportListReadDTO;
import com.developer.postservice.dto.ResponseUserDTO;
import com.developer.postservice.admin.query.dto.RecruitApplyDetailReadDTO;
import com.developer.postservice.admin.query.dto.RecruitApplyListReadDTO;
import com.developer.postservice.admin.query.dto.ReportDetailReadDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {

    // 채용공고 등록 신청 내역 목록 조회
    List<RecruitApplyListReadDTO> readRecruitApplyList(int offset);

    // 채용공고 등록 신청 상세 내역 조회
    RecruitApplyDetailReadDTO readRecruitApplyDetailById(@Param("recruitCode") Long recruitCode);

    // 신고 목록 조회
    List<ReportListReadDTO> readReportList(int offset);

    // 신고 상세 내용 조회
    ReportDetailReadDTO readReportDetailById(@Param("repoCode") Long repoCode);
}
