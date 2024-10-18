package com.developer.userservice.admin.query.service;

import com.developer.userservice.admin.query.mapper.AdminMapper;
import com.developer.userservice.user.query.dto.ResponseUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminQueryService {

    private final AdminMapper adminMapper;

    // ========= 사용자 =========
    // 사용자 상태별 User 객체 찾기
    public List<ResponseUserDTO> findAllByUserStatus(String userStatus){

        return adminMapper.findAllByUserStatus(userStatus);
    }

    // 신고 10회 이상 User 확인하기
    public List<ResponseUserDTO> findAllByUserWarning(){

        return adminMapper.findAllByUserWarning();
    }
}
