package com.developer.user.query.mapper;

import com.developer.user.query.dto.FindIdDTO;
import com.developer.user.query.dto.ResponseUserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;


@Mapper
public interface UserMapper {

    // 유저 코드로 조회
    ResponseUserDTO findByUserCode(Long userCode);
    
    // 유저 아이디로 조회
    ResponseUserDTO findByUserId(String userId);

    // 이름, 핸드폰번호로 userId 조회
    Optional<String> findUserIdByUserNameAndUserPhone(FindIdDTO findIdDTO);
    
    // 닉네임 중복 확인
    ResponseUserDTO findByUserNick(String userNick);

    // 핸드폰 번호 중복 확인
    ResponseUserDTO findByUserPhone(String userPhone);
}
