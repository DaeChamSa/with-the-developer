package com.developer.prefix.query.mapper;

import com.developer.prefix.query.dto.MapperPrefixDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PrefixMapper {

    MapperPrefixDTO findByUserCode(Long userCode);
}
