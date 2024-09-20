package com.developer.user.query.mapper;

import com.developer.user.query.dto.ResponseUserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    // 유저 코드로 조회
    ResponseUserDTO findByUserCode(Long userCode);
    
    // 유저 아이디로 조회
    ResponseUserDTO findByUserId(String userId);

    // 유저 이메일로 아이디 조회
    String findUserIdByEmail(String userEmail);

    // 유저 상태별 회원 조회
    List<ResponseUserDTO> findAllByUserStatus(String userStatus);

    // 유저 신고횟수 10회 초과 조회
    List<ResponseUserDTO> findAllByUserWarning();
}
