package com.developer.prefix.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.dbti.command.domain.aggregate.Dbti;
import com.developer.dbti.command.domain.repository.DbtiRepository;
import com.developer.jobTag.command.entity.JobTag;
import com.developer.jobTag.command.repository.JobTagRepository;
import com.developer.prefix.query.dto.MapperPrefixDTO;
import com.developer.prefix.query.dto.ResponsePrefixDTO;
import com.developer.prefix.query.mapper.PrefixMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrefixQueryService {

    private final PrefixMapper prefixMapper;
    private final DbtiRepository dbtiRepository;
    private final JobTagRepository jobTagRepository;

    // 본인 성향 조회
    public ResponsePrefixDTO findByUserCode(Long userCode) {

        MapperPrefixDTO byUserCode = prefixMapper.findByUserCode(userCode);

        if (byUserCode == null){
            throw new CustomException(ErrorCode.NOT_FOUND_PREFIX);
        }

        JobTag jobTag = jobTagRepository.findById(byUserCode
                .getJobTagCode()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_JOB_TAG));

        Dbti dbti = dbtiRepository.findById(byUserCode.getDbtiCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_DBTI));

        ResponsePrefixDTO prefixDTO = new ResponsePrefixDTO(dbti.getDbtiValue(), jobTag.getJobTagName());

        return prefixDTO;
    }
}
