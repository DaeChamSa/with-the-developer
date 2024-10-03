package com.developer.noti.command.application.controller;

import com.developer.common.success.SuccessCode;
import com.developer.config.TokenDecoder;
import com.developer.noti.command.application.dto.NotiCommentCreateDTO;
import com.developer.noti.command.application.service.NotiCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/noti")
@RequiredArgsConstructor
public class NotiCommandController {

    private final NotiCommandService notiCommandService;
    private final TokenDecoder tokenDecoder;

    // 알림 읽음처리
    @GetMapping("/read/{notiCode}")
    public ResponseEntity<SuccessCode> readNoti(
            @RequestHeader("Authorization") String token,
            @PathVariable(name = "notiCode") Long notiCode
    ) {
        Long currentUserCode = tokenDecoder.getUserCodeFromToken(token);

        notiCommandService.readNoti(currentUserCode, notiCode);

        return ResponseEntity.ok(SuccessCode.NOTI_READ_OK);
    }

    // 알림 삭제처리
    @DeleteMapping("/delete/{notiCode}")
    public ResponseEntity<SuccessCode> deleteNoti(
            @RequestHeader("Authorization") String token,
            @PathVariable(name = "notiCode") Long notiCode
    ) {
        Long currentUserCode = tokenDecoder.getUserCodeFromToken(token);

        notiCommandService.deleteNoti(currentUserCode, notiCode);

        return ResponseEntity.ok(SuccessCode.NOTI_DELETE_OK);
    }

    // 테스트하기 위해 만들어놓음
    @PostMapping("/create")
    public ResponseEntity<?> createNoti(@RequestBody NotiCommentCreateDTO notiCommentCreateDTO) {
        notiCommandService.addCommentEvent(notiCommentCreateDTO);

        return ResponseEntity.ok().build();
    }
}
