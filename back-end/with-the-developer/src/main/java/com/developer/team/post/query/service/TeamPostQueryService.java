package com.developer.team.post.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.image.command.repository.ImageRepository;
import com.developer.team.post.query.dto.TeamPostDTO;
import com.developer.team.post.query.dto.TeamPostListDTO;
import com.developer.team.post.query.mapper.TeamPostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamPostQueryService {

    private final TeamPostMapper teamPostMapper;
    private final ImageRepository imageRepository;

    // 팀 모집 게시글 코드로 조회
    public TeamPostDTO selectByTeamPostCode(Long teamPostCode) {
        TeamPostDTO teamPostDTO = teamPostMapper.selectByTeamPostCode(teamPostCode);
        log.info(teamPostCode+"조회 시작");
        // 해당 게시글이 없다면 예외 발생
        if (teamPostDTO == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }
        teamPostDTO.setImages(imageRepository.findByTeamPostCode(teamPostCode));

        return teamPostDTO;
    }

    public List<TeamPostListDTO> selectAllTeamPost(Integer page) {
        // paging offset 생성
        int offset = (page - 1) * 10;

        List<TeamPostListDTO> teamPostList = teamPostMapper.selectAllTeamPost(offset);

        return teamPostList;
    }

    public List<TeamPostListDTO> selectByTags(String searchTag, Integer page) {

        int offset = (page - 1) * 10;

        Map<String, Object> params = new HashMap<>();
        params.put("tag", searchTag);
        params.put("offset", offset);
        log.info("태그 검색 서비스 실행");
        List<TeamPostListDTO> searchList = teamPostMapper.selectByTags(params);
        log.info(searchList.toString());
        return searchList;
    }
}
