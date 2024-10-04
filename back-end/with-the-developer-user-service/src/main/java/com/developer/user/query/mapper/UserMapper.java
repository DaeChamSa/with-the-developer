package com.developer.user.query.mapper;

import com.developer.user.query.dto.ResponseUserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    // 유저 코드로 조회
    ResponseUserDTO findByUserCode(Long userCode);
    
    // 유저 아이디로 조회
    ResponseUserDTO findByUserId(String userId);

    // 유저 이메일로 아이디 조회
    String findUserIdByEmail(String userEmail);


}
