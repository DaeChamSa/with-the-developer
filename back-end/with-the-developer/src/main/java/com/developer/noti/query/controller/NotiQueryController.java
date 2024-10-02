package com.developer.noti.query.controller;

import com.developer.common.success.SuccessCode;
import com.developer.noti.query.dto.ResponseNotiDTO;
import com.developer.noti.query.service.NotiQueryService;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "notification", description = "알림 API")
@RestController
@RequestMapping("/noti")
@RequiredArgsConstructor
public class NotiQueryController {

    private final NotiQueryService notiQueryService;

    // 로그인 된 유저의 모든 알림 조회
    @GetMapping("/received")
    @Operation(summary = "알림 목록 조회", description = "받은 알림 목록을 조회합니다.")
    public ResponseEntity<?> findAll(){
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        List<ResponseNotiDTO> allByUserCode = notiQueryService.findAllByUserCode(currentUserCode);
        if (allByUserCode.isEmpty()){
            return ResponseEntity.ok(SuccessCode.NOT_FOUND_NOTI_OK.getMessage());
        }

        return ResponseEntity.ok(allByUserCode);
    }

    // 읽은 알림들 조회
    @GetMapping("/is-read")
    @Operation(summary = "읽은 알림 목록 조회", description = "받은 알림 중 읽은 알림 목록을 조회합니다.")
    public ResponseEntity<List<ResponseNotiDTO>> isRead(){
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        return ResponseEntity.ok(notiQueryService.findByIsRead(currentUserCode));
    }

    // 안읽은 알림들 조회
    @GetMapping("/not-read")
    @Operation(summary = "읽지 않은 알림 목록 조회", description = "받은 알림 중 읽지 않은 알림 목록을 조회합니다.")
    public ResponseEntity<List<ResponseNotiDTO>> notRead(){
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        return ResponseEntity.ok(notiQueryService.findByNotRead(currentUserCode));
    }
}
