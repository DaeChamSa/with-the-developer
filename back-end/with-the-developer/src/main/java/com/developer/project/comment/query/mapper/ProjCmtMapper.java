package com.developer.project.comment.query.mapper;

import com.developer.project.comment.query.dto.ProjCmtResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjCmtMapper {

    List<ProjCmtResponseDTO> findAll(Map<String, Object> params);
}
