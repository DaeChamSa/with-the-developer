package com.developer.userservice.user.query.dto;

import lombok.Data;

@Data
public class ResponseUserDTO {

    private Long userCode;          // 유저 고유 코드

    private String userId;          // 유저 아이디 == 유저이메일

    private String userName;        // 사용자 이름

    private String userNick;        // 닉네임

    private String userBirth;       // 사용자 생일

    private String userPhone;       // 사용자 폰번호

    private int userWarning;        // 신고 당한 횟수

    private String userStatus;      // 유저 현재 상태


    
    private boolean resNoti;        // 알림 수신 여부
}
