package com.developer.block.query.mapper;

import com.developer.block.query.dto.BlockResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlockMapper {

    List<BlockResponseDTO> findAll(Long blockerCode);
}
