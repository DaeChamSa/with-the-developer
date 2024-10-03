package com.developer.noti.query.controller;

import com.developer.common.success.SuccessCode;
import com.developer.config.TokenDecoder;
import com.developer.noti.query.dto.ResponseNotiDTO;
import com.developer.noti.query.service.NotiQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/noti")
@RequiredArgsConstructor
public class NotiQueryController {

    private final NotiQueryService notiQueryService;
    private final TokenDecoder tokenDecoder;

    // 로그인 된 유저의 모든 알림 조회
    @GetMapping("/received")
    public ResponseEntity<?> findAll(
            @RequestHeader("Authorization") String token
    ){
        Long currentUserCode = tokenDecoder.getUserCodeFromToken(token);

        List<ResponseNotiDTO> allByUserCode = notiQueryService.findAllByUserCode(currentUserCode);
        if (allByUserCode.isEmpty()){
            return ResponseEntity.ok(SuccessCode.NOT_FOUND_NOTI_OK.getMessage());
        }

        return ResponseEntity.ok(allByUserCode);
    }

    // 읽은 알림들 조회
    @GetMapping("/is-read")
    public ResponseEntity<List<ResponseNotiDTO>> isRead(
            @RequestHeader("Authorization") String token
    ){
        Long currentUserCode = tokenDecoder.getUserCodeFromToken(token);

        return ResponseEntity.ok(notiQueryService.findByIsRead(currentUserCode));
    }
    // 안읽은 알림들 조회
    @GetMapping("/not-read")
    public ResponseEntity<List<ResponseNotiDTO>> notRead(
            @RequestHeader("Authorization") String token
    ){
        Long currentUserCode = tokenDecoder.getUserCodeFromToken(token);

        return ResponseEntity.ok(notiQueryService.findByNotRead(currentUserCode));
    }

}
