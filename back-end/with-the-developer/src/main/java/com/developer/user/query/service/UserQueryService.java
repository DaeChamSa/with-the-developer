package com.developer.user.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.user.query.dto.ResponseUserDTO;
import com.developer.user.query.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserMapper userMapper;

    // 사용자 코드로 User 객체 찾기
    public ResponseUserDTO findByUserCode(Long userCode){

        ResponseUserDTO byUserCode = userMapper.findByUserCode(userCode);
        checkNull(byUserCode);

        return byUserCode;
    }
    // 사용자 아이디로 User 객체 찾기
    public ResponseUserDTO findByUserID(String userId){

        ResponseUserDTO byUserId = userMapper.findByUserId(userId);
        checkNull(byUserId);

        return byUserId;
    }

    private ResponseUserDTO checkNull(ResponseUserDTO responseUserDTO){

        if (responseUserDTO == null){
            log.info("아이디가 존재하지 않음 {}", responseUserDTO);
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        return responseUserDTO;
    }
}
