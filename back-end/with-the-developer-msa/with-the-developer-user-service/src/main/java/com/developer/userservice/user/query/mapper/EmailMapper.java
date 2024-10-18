package com.developer.userservice.user.query.mapper;

import com.developer.userservice.user.query.dto.CheckCodeDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {

    CheckCodeDTO findEmailByCode(String code);
}
