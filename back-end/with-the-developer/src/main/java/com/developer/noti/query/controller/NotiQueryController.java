package com.developer.noti.query.controller;

import com.developer.common.success.SuccessCode;
import com.developer.noti.query.dto.ResponseNotiDTO;
import com.developer.noti.query.service.NotiQueryService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/noti")
@RequiredArgsConstructor
public class NotiQueryController {

    private final NotiQueryService notiQueryService;

    // 로그인 된 유저의 모든 알림 조회
    @GetMapping("/received")
    public ResponseEntity<?> findAll(){
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        List<ResponseNotiDTO> allByUserCode = notiQueryService.findAllByUserCode(currentUserCode);
        if (allByUserCode.isEmpty()){
            return ResponseEntity.ok(SuccessCode.NOT_FOUND_NOTI_OK.getMessage());
        }

        return ResponseEntity.ok(allByUserCode);
    }

}
