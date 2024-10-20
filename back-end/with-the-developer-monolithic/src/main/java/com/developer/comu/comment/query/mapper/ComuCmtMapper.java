package com.developer.comu.comment.query.mapper;

import com.developer.comu.comment.query.dto.ComuCmtDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ComuCmtMapper {
    List<ComuCmtDTO> selectComuCmtByPostCode(Long comuPostCode, int offset, int limit);
}
