package com.developer.admin.query.service;

import com.developer.admin.query.dto.RecruitListReadDTO;
import com.developer.admin.query.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminQueryService {
    private final SqlSession sqlSession;

    // 채용공고 등록 신청 내역 목록 조회
    public List<RecruitListReadDTO> readRecruitApplyList() {
        AdminMapper adminMapper = sqlSession.getMapper(AdminMapper.class);

        return adminMapper.readRecruitApplyList();
    }

}
