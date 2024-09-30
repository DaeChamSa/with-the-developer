package com.developer.team.post.query.mapper;

import com.developer.team.post.query.dto.TeamPostDTO;
import com.developer.team.post.query.dto.TeamPostListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeamPostMapper {

    TeamPostDTO selectByTeamPostCode(@Param("teamPostCode") Long teamPostCode);

    List<TeamPostListDTO> selectAllTeamPost(int offset);

    List<TeamPostListDTO> selectByTags(Map<String, Object> params);
}
