package com.developer.project.post.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.project.post.query.dto.ProjPostListResponseDTO;
import com.developer.project.post.query.dto.ProjPostResponseDTO;
import com.developer.project.post.query.mapper.ProjPostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjPostQueryService {

    private final ProjPostMapper projPostMapper;

    @Transactional(readOnly = true)
    public List<ProjPostListResponseDTO> readAll(Integer page) {
        int offset = (page - 1) * 10;

        List<ProjPostListResponseDTO> projPostList = projPostMapper.findAll(offset);

        if (projPostList == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }

        return projPostList;
    }

    @Transactional(readOnly = true)
    public ProjPostResponseDTO readByCode(Long projPostCode) {
        ProjPostResponseDTO projPostResponseDTO = projPostMapper.findByCode(projPostCode);

        if (projPostResponseDTO == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }

        return projPostResponseDTO;
    }
}
