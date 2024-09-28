package com.developer.recruit.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.recruit.query.dto.RecruitDetailReadDTO;
import com.developer.recruit.query.dto.RecruitListReadDTO;
import com.developer.recruit.query.mapper.RecruitMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitQueryService {

    private final RecruitMapper recruitMapper;
    // 등록된 채용공고 목록 조회
    public List<RecruitListReadDTO> readRecruitList(Integer page) {
        if (page == null || page <= 0) {
            throw new CustomException(ErrorCode.INVALID_VALUE);
        }

        int offset = (page - 1) * 10;

        List<RecruitListReadDTO> recruitList = recruitMapper.readRecruitList(offset);

        if (recruitList == null) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return recruitMapper.readRecruitList(offset);
    }


    // 등록된 채용공고 상세내역 조회
    public RecruitDetailReadDTO readRecruitDetailById(Long id) {
        return recruitMapper.readRecruitDetailById(id);
    }
}
