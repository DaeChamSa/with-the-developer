package com.developer.dbti.command.application.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.dbti.command.application.dto.DbtiAddDTO;
import com.developer.dbti.command.domain.aggregate.Dbti;
import com.developer.dbti.command.domain.aggregate.DbtiRole;
import com.developer.dbti.command.domain.repository.DbtiRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DbtiCommandService {

    private final DbtiRepository dbtiRepository;

    // Dbti 성향 추가
    @Transactional
    public void addDbti(DbtiAddDTO dbtiAddDTO){

        Dbti dbti = new Dbti(dbtiAddDTO.getValue(), DbtiRole.valueOf(dbtiAddDTO.getDbtiRole()));

        dbtiRepository.save(dbti);
    }

    // 성향 delete
    @Transactional
    public void deleteDbti(Long id){

        Dbti dbti = dbtiRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_DBTI));
        dbtiRepository.delete(dbti);
    }

}
