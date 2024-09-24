package com.developer.recruit.query.service;

import com.developer.recruit.query.dto.RecruitDetailReadDTO;
import com.developer.recruit.query.dto.RecruitListReadDTO;
import com.developer.recruit.query.mapper.RecruitMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitQueryService {
    private final SqlSession sqlSession;

    // 등록된 채용공고 목록 조회
    public List<RecruitListReadDTO> readRecruitList(Integer page) {

        int offset = (page - 1) * 10;

        RecruitMapper recruitMapper = sqlSession.getMapper(RecruitMapper.class);

        return recruitMapper.readRecruitList(offset);
    }


    // 등록된 채용공고 상세내역 조회
    public RecruitDetailReadDTO readRecruitDetailById(Long id) {

        RecruitMapper recruitMapper = sqlSession.getMapper(RecruitMapper.class);

        return recruitMapper.readRecruitDetailById(id);
    }
}
