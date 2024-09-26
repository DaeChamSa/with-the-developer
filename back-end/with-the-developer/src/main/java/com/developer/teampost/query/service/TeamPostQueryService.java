package com.developer.teampost.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.teampost.query.dto.TeamPostDTO;
import com.developer.teampost.query.dto.TeamPostListDTO;
import com.developer.teampost.query.mapper.TeamPostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamPostQueryService {

    private final TeamPostMapper teamPostMapper;

    // 팀 모집 게시글 코드로 조회
    public TeamPostDTO selectByTeamPostCode(Long teamPostCode) {
        TeamPostDTO teamPostDTO = teamPostMapper.selectByTeamPostCode(teamPostCode);

        // 해당 게시글이 없다면 예외 발생
        if (teamPostDTO == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }

        return teamPostDTO;
    }

    public List<TeamPostListDTO> selectAllTeamPost(Integer page) {
        // paging offset 생성
        int offset = (page - 1) * 10;

        List<TeamPostListDTO> teamPostList = teamPostMapper.selectAllTeamPost(offset);

        return teamPostList;
    }
}
