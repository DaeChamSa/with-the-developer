package com.developer.user.query.mapper;

import com.developer.user.query.dto.CheckCodeDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {

    CheckCodeDTO findEmailByCode(String code);
}
