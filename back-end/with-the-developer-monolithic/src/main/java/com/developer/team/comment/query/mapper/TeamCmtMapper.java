package com.developer.team.comment.query.mapper;

import com.developer.team.comment.query.dto.ResponseTeamCmtListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface TeamCmtMapper {
    List<ResponseTeamCmtListDTO> selectTeamCmtList(Map<String, Object> params);
}
