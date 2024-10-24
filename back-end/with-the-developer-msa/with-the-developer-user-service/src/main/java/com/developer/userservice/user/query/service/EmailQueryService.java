package com.developer.userservice.user.query.service;

import com.developer.userservice.common.exception.CustomException;
import com.developer.userservice.common.exception.ErrorCode;
import com.developer.userservice.user.query.dto.CheckCodeDTO;
import com.developer.userservice.user.query.mapper.EmailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service("EmailQueryService")
@RequiredArgsConstructor
@Slf4j
public class EmailQueryService {

    private final EmailMapper emailMapper;

    public void checkCode(CheckCodeDTO checkCodeDTO){

        log.info("code {}", checkCodeDTO.getCode());

        CheckCodeDTO emailByCode = emailMapper.findEmailByCode(checkCodeDTO.getCode());

        log.info("emailByCode {}", emailByCode);

        LocalDateTime entityDate = emailByCode.getSendDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        log.info("entityDate {}", entityDate);

        LocalDateTime dtoDate = checkCodeDTO.getSendDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        log.info("dtoDate {}", dtoDate);

        // DTO로 받은 sendDate와의 차이 계산
        Duration duration = Duration.between(entityDate, dtoDate);

        if (duration.toMinutes() > 10){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
