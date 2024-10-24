package com.developer.user.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.user.query.dto.FindIdDTO;
import com.developer.user.query.dto.ResponseUserDTO;
import com.developer.user.query.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


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

    // 이름, 핸드폰 번호로 userId 찾고 반환
    public String findId(FindIdDTO findIdDTO){
        String userId = userMapper.findUserIdByUserNameAndUserPhone(findIdDTO)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        log.info("userId {}", userId);
        if (userId != null){
            // @의 index값 찾기
            int i = userId.indexOf("@");
            // 0 ~ @앞의 3자리까지
            String substring = userId.substring(0, i-2);
            String masked = "***";
            String backSub = userId.substring(i);
            return substring + masked + backSub;
        }
        return null;
    }

    // 아이디 중복 확인
    public boolean checkId(String userId){
        ResponseUserDTO byUserId = userMapper.findByUserId(userId);
        return byUserId == null;
    }

    // 닉네임 중복 확인
    public boolean checkNick(String userNick){
        ResponseUserDTO byUserNick = userMapper.findByUserNick(userNick);
        return byUserNick == null;
    }

    // 휴대폰 번호 중복 확인
    public boolean checkPhone(String userPhone){
        ResponseUserDTO byUserPhone = userMapper.findByUserPhone(userPhone);
        return byUserPhone == null;
    }

    // Null 체킹
    private ResponseUserDTO checkNull(ResponseUserDTO responseUserDTO){

        if (responseUserDTO == null){
            log.info("아이디가 존재하지 않음 {}", responseUserDTO);
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        return responseUserDTO;
    }
    

}