package com.developer.project.query.mapper;

import com.developer.project.query.dto.ProjPostResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjPostMapper {

    List<ProjPostResponseDTO> findAll(int offset);

    ProjPostResponseDTO findByCode(@Param("projPostCode") Long projPostCode);
}
