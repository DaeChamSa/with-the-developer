package com.developer.postservice.recruit.query.service;

import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.image.command.repository.ImageRepository;
import com.developer.postservice.recruit.command.entity.ApprStatus;
import com.developer.postservice.recruit.command.entity.Recruit;
import com.developer.postservice.recruit.command.entity.RecruitStatus;
import com.developer.postservice.recruit.command.repository.RecruitRepository;
import com.developer.postservice.recruit.query.mapper.RecruitMapper;
import com.developer.postservice.recruit.command.entity.ApprStatus;
import com.developer.postservice.recruit.command.entity.Recruit;
import com.developer.postservice.recruit.command.entity.RecruitStatus;
import com.developer.postservice.recruit.command.repository.RecruitRepository;
import com.developer.postservice.recruit.query.dto.RecruitDetailReadDTO;
import com.developer.postservice.recruit.query.dto.RecruitListReadDTO;
import com.developer.postservice.recruit.query.mapper.RecruitMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitQueryService {

    private final RecruitMapper recruitMapper;
    private final RecruitRepository recruitRepository;
    private final ImageRepository imageRepository;

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
        Recruit recruit = recruitRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        if (recruit.getRecruitApprStatus() == ApprStatus.APPROVE
                && recruit.getRecruitStatus() != RecruitStatus.DELETE) {
            RecruitDetailReadDTO recruitDetail = recruitMapper.readRecruitDetailById(id);
            recruitDetail.setImages(imageRepository.findByRecruitCode(id));

            return recruitDetail;
        } else {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }
    }

    public List<RecruitListReadDTO> searchRecruitByTag(String searchTag, Integer page) {

        int offset = (page - 1) * 10;

        Map<String, Object> params = new HashMap<>();
        params.put("tag", searchTag);
        params.put("offset", offset);
        log.info("태그 검색 시작");
        List<RecruitListReadDTO> searchList = recruitMapper.searchRecruitByTags(params);
        log.info(searchList.toString());
        return searchList;
    }
}
