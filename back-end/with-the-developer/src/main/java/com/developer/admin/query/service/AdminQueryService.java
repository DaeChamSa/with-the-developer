package com.developer.admin.query.service;

import com.developer.admin.query.dto.RecruitApplyDetailReadDTO;
import com.developer.admin.query.dto.RecruitApplyListReadDTO;
import com.developer.admin.query.mapper.AdminMapper;
import com.developer.recruit.command.dto.RecruitApplyDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminQueryService {

    private final SqlSession sqlSession;

    // 채용공고 등록 신청 내역 목록 조회
    public List<RecruitApplyListReadDTO> readRecruitApplyList() {

        AdminMapper adminMapper = sqlSession.getMapper(AdminMapper.class);

        return adminMapper.readRecruitApplyList();
    }


    // 채용공고 등록 신청 상세내역 조회
    public RecruitApplyDetailReadDTO readRecruitApplyDetailById(Long id) {

        AdminMapper adminMapper = sqlSession.getMapper(AdminMapper.class);

        return adminMapper.readRecruitApplyDetailById(id);
    }

}
