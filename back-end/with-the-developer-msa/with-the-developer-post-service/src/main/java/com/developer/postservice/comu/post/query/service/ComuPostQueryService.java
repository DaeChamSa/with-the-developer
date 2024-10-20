package com.developer.postservice.comu.post.query.service;

import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.comu.post.query.dto.ComuPostResponseDTO;
import com.developer.postservice.comu.post.query.mapper.ComuPostMapper;
import com.developer.postservice.image.command.repository.ImageRepository;
import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.comu.post.query.mapper.ComuPostMapper;
import com.developer.postservice.image.command.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComuPostQueryService {

    private final ComuPostMapper comuPostMapper;
    private final ImageRepository imageRepository;

    // 커뮤니티 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<ComuPostResponseDTO> selectAllComuPost(Integer page) {
        int offset = (page - 1) * 10;

        return comuPostMapper.selectAllComuPost(offset);
    }

    //커뮤니티 게시글 코드로 조회
    @Transactional(readOnly = true)
    public ComuPostResponseDTO selectComuPostByCode(Long comuPostCode) {
        ComuPostResponseDTO comuPostResponseDTO = comuPostMapper.selectComuPostByCode(comuPostCode);

        if(comuPostCode == null || comuPostResponseDTO == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }

        comuPostResponseDTO.setImages(imageRepository.findByComuCode(comuPostCode));

        return comuPostResponseDTO;
    }
}
