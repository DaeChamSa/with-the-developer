package com.developer.userservice.admin.query.mapper;

import com.developer.userservice.user.query.dto.ResponseUserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    // 사용자
    // 유저 상태별 회원 조회
    List<ResponseUserDTO> findAllByUserStatus(String userStatus);

    // 유저 신고횟수 10회 초과 조회
    List<ResponseUserDTO> findAllByUserWarning();
}
