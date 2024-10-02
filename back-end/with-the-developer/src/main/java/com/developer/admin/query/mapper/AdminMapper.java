package com.developer.admin.query.mapper;

import com.developer.admin.query.dto.RecruitApplyDetailReadDTO;
import com.developer.admin.query.dto.RecruitApplyListReadDTO;
import com.developer.admin.query.dto.ReportDetailReadDTO;
import com.developer.admin.query.dto.ReportListReadDTO;
import com.developer.user.query.dto.ResponseUserDTO;
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

    // 사용자
    // 유저 상태별 회원 조회
    List<ResponseUserDTO> findAllByUserStatus(String userStatus);

    // 유저 신고횟수 10회 초과 조회
    List<ResponseUserDTO> findAllByUserWarning();
}
