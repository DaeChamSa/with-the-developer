package com.developer.prefix.command.application.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.dbti.command.domain.repository.DbtiRepository;
import com.developer.jobTag.command.repository.JobTagRepository;
import com.developer.prefix.command.application.dto.PrefixCreateDTO;
import com.developer.prefix.command.domain.aggregate.Prefix;
import com.developer.prefix.command.domain.repository.PrefixRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrefixCommandService {

    private final PrefixRepository prefixRepository;
    private final JobTagRepository jobTagRepository;
    private final DbtiRepository dbtiRepository;

    // 수식어 생성
    @Transactional
    public void prefixCreate(Long userCode, PrefixCreateDTO prefixCreateDTO){

        jobTagRepository.findById(prefixCreateDTO.getJobTagCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_JOB_TAG));

        dbtiRepository.findById(prefixCreateDTO.getDbtiCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_DBTI));

        Prefix prefix = Prefix.prefixCreate(userCode, prefixCreateDTO);

        prefixRepository.save(prefix);
    }
}
