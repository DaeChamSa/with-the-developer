package com.developer.user.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.user.query.dto.CheckCodeDTO;
import com.developer.user.query.dto.FindIdDTO;
import com.developer.user.query.mapper.EmailMapper;
import com.developer.user.query.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service("EmailQueryService")
@RequiredArgsConstructor
@Slf4j
public class EmailQueryService {

    private final SqlSessionTemplate sqlSession;

    public void checkCode(CheckCodeDTO checkCodeDTO){

        log.info("code {}", checkCodeDTO.getCode());

        CheckCodeDTO emailByCode = sqlSession.getMapper(EmailMapper.class).findEmailByCode(checkCodeDTO.getCode());

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

    public String findId(FindIdDTO code){

        log.info("code {}", code);

        CheckCodeDTO emailByCode = sqlSession.getMapper(EmailMapper.class).findEmailByCode(code.getCode());

        log.info("emailByCode {}", emailByCode);

        if (emailByCode != null){
            String userIdByEmail = sqlSession.getMapper(UserMapper.class).findUserIdByEmail(emailByCode.getUserEmail());
            log.info("userIdByEmail {}", userIdByEmail);

            if (userIdByEmail != null){
                String substring = userIdByEmail.substring(0, userIdByEmail.length() - 3);
                String masked = "***";

                return substring + masked;
            }

            return null;
        }

        return null;
    }
}
