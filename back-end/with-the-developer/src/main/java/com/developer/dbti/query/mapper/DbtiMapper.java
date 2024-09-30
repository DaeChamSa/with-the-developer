package com.developer.dbti.query.mapper;

import com.developer.dbti.query.dto.ResponseDbtiDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DbtiMapper {

    List<String> findByDbtiRole(String dbtiRole);

    List<ResponseDbtiDTO> findAll();
}
