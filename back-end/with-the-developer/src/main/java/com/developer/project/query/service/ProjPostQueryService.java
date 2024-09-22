package com.developer.project.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.project.query.dto.ProjPostResponseDTO;
import com.developer.project.query.mapper.ProjPostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjPostQueryService {

    private final SqlSession sqlSession;

    @Transactional(readOnly = true)
    public List<ProjPostResponseDTO> readAll(Integer page) {

        int offset = (page - 1) * 10;

        List<ProjPostResponseDTO> projPostList = sqlSession.getMapper(ProjPostMapper.class).findAll(offset);

        if (projPostList == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_PROJ_POST);
        }

        return projPostList;
    }

    @Transactional(readOnly = true)
    public ProjPostResponseDTO readByCode(Long projPostCode) {
        ProjPostResponseDTO projPostResponseDTO = sqlSession.getMapper(ProjPostMapper.class).findByCode(projPostCode);

        if (projPostResponseDTO == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_PROJ_POST);
        }

        return projPostResponseDTO;
    }
}
