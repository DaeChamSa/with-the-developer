package com.developer.admin.query.service;

import com.developer.admin.query.dto.RecruitApplyDetailReadDTO;
import com.developer.admin.query.dto.RecruitApplyListReadDTO;
import com.developer.admin.query.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminQueryService {

    private final SqlSession sqlSession;

//    private final AdminMapper adminMapper;
    // 채용공고 등록 신청 내역 목록 조회
    public List<RecruitApplyListReadDTO> readRecruitApplyList(Integer page) {
        int offset = (page - 1) * 10;
        return sqlSession.getMapper(AdminMapper.class).readRecruitApplyList(offset);
//        return adminMapper.readRecruitApplyList(offset);
    }


    // 채용공고 등록 신청 상세내역 조회
    public RecruitApplyDetailReadDTO readRecruitApplyDetailById(Long id) {

        return sqlSession.getMapper(AdminMapper.class).readRecruitApplyDetailById(id);
//        return adminMapper.readRecruitApplyDetailById(id);
    }

}
