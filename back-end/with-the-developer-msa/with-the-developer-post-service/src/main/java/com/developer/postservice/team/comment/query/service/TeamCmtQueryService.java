package com.developer.postservice.team.comment.query.service;

import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.postservice.team.comment.query.dto.ResponseTeamCmtListDTO;
import com.developer.postservice.team.comment.query.mapper.TeamCmtMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeamCmtQueryService {

    private final TeamCmtMapper teamCmtMapper;

    public List<ResponseTeamCmtListDTO> selectTeamCmtList(Long teamPostCode, Integer page) {

        int offset = (page - 1) * 10;

        Map<String, Object> params = new HashMap<>();
        params.put("teamPostCode", teamPostCode);
        params.put("offset", offset);
        List<ResponseTeamCmtListDTO> teamCmtList = teamCmtMapper.selectTeamCmtList(params);

        if (teamCmtList == null || teamCmtList.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_COMMENT);
        }
        return teamCmtList;
    }
}
