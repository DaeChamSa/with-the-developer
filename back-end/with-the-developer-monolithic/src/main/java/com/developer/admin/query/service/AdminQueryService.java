package com.developer.admin.query.service;

import com.developer.admin.query.dto.RecruitApplyDetailReadDTO;
import com.developer.admin.query.dto.RecruitApplyListReadDTO;
import com.developer.admin.query.dto.ReportDetailReadDTO;
import com.developer.admin.query.dto.ReportListReadDTO;
import com.developer.admin.query.mapper.AdminMapper;
import com.developer.user.query.dto.ResponseUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminQueryService {

    private final AdminMapper adminMapper;

    // ========= 사용자 =========
    // 사용자 상태별 User 객체 찾기
    public List<ResponseUserDTO> findAllByUserStatus(String userStatus){

        return adminMapper.findAllByUserStatus(userStatus);
    }

    // 신고 10회 이상 User 확인하기
    public List<ResponseUserDTO> findAllByUserWarning(){

        return adminMapper.findAllByUserWarning();
    }

    // 채용공고 등록 신청 내역 목록 조회
    public List<RecruitApplyListReadDTO> readRecruitApplyList(Integer page) {
        int offset = (page - 1) * 10;

        return adminMapper.readRecruitApplyList(offset);
    }


    // 채용공고 등록 신청 상세내역 조회
    public RecruitApplyDetailReadDTO readRecruitApplyDetailById(Long id) {

        return adminMapper.readRecruitApplyDetailById(id);
    }

    // 신고 목록 조회
    public List<ReportListReadDTO>readReportList(Integer page) {
        int offset = (page - 1) * 10;
        return adminMapper.readReportList(offset);
    }

    // 신고 상세 내용 조회
    public ReportDetailReadDTO readReportDetailById(Long id) {
        return adminMapper.readReportDetailById(id);
    }
}
