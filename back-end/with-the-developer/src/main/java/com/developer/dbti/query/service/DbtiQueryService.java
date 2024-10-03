package com.developer.dbti.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.dbti.command.domain.aggregate.DbtiRole;
import com.developer.dbti.query.dto.ResponseDbtiDTO;
import com.developer.dbti.query.mapper.DbtiMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DbtiQueryService {

    private final DbtiMapper dbtiMapper;

    public List<String> dbtiResult(String dbtiRole){

        if (!DbtiRole.contains(dbtiRole.toUpperCase())){
            log.info("해당하는 역할이 존재하지 않음 {}", dbtiRole);
            throw new CustomException(ErrorCode.NOT_MATCH_DBTI_ROLE);
        }

        List<String> byDbtiRole = dbtiMapper.findByDbtiRole(dbtiRole);

        if (byDbtiRole.isEmpty()) {
            log.info("byDbtiRole가 null 입니다. {}", byDbtiRole);
            throw new CustomException(ErrorCode.NOT_FOUND_DBTI);
        }

        return byDbtiRole;
    }

    public List<ResponseDbtiDTO> findAll(){
        List<ResponseDbtiDTO> all = dbtiMapper.findAll();

        if (all.isEmpty()) {
            log.info("all is Null {}", all);
        }

        return all;
    }
}
