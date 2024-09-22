package com.developer.comu.query.mapper;

import com.developer.comu.query.dto.ComuPostResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ComuPostMapper {

    List<ComuPostResponseDTO> selectAllComuPost(int offset);

    ComuPostResponseDTO selectComuPostByCode(@Param("comuPostCode")Long comuPostCode);
}
