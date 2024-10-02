package com.developer.project.post.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.image.command.repository.ImageRepository;
import com.developer.project.post.query.dto.ProjPostListResponseDTO;
import com.developer.project.post.query.dto.ProjPostResponseDTO;
import com.developer.project.post.query.mapper.ProjPostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjPostQueryService {

    private final ProjPostMapper projPostMapper;
    private final ImageRepository imageRepository;

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
        projPostResponseDTO.setImages(imageRepository.findByProjPostCode(projPostCode));

        return projPostResponseDTO;
    }

    public List<ProjPostListResponseDTO> searchByTag(String searchTag, Integer page) {

        int offset = (page - 1) * 10;

        Map<String, Object> params = new HashMap<>();
        params.put("tag", searchTag);
        params.put("offset", offset);

        List<ProjPostListResponseDTO> searchList = projPostMapper.findByProjTag(params);

        return searchList;
    }
}
