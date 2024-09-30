package com.developer.project.post.query.mapper;

import com.developer.project.post.query.dto.ProjPostListResponseDTO;
import com.developer.project.post.query.dto.ProjPostResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjPostMapper {

    List<ProjPostListResponseDTO> findAll(int offset);

    ProjPostResponseDTO findByCode(@Param("projPostCode") Long projPostCode);

    List<ProjPostListResponseDTO> findByProjTag(Map<String, Object> params);
}
