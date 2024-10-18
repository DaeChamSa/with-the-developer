package com.developer.postservice.prefix.query.mapper;

import com.developer.postservice.prefix.query.dto.MapperPrefixDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PrefixMapper {

    MapperPrefixDTO findByUserCode(Long userCode);
}
