package com.developer.user.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.user.query.dto.ResponseUserDTO;
import com.developer.user.query.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final SqlSessionTemplate sqlSession;

    // 사용자 아이디로 User 객체 찾기
    public ResponseUserDTO findByUserID(String userId){

        ResponseUserDTO byUserId = sqlSession.getMapper(UserMapper.class).findByUserId(userId);

        if (byUserId == null){
            log.info("아이디가 존재하지 않음 {}", userId);
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        return byUserId;
    }

    // 사용자 상태별 User 객체 찾기
    public List<ResponseUserDTO> findAllByUserStatus(String userStatus){

        return sqlSession.getMapper(UserMapper.class).findAllByUserStatus(userStatus);
    }

    // 신고 10회 이상 User 확인하기
    public List<ResponseUserDTO> findAllByUserWarning(){

        return sqlSession.getMapper(UserMapper.class).findAllByUserWarning();
    }
}
