package com.developer.noti.command.application.controller;

import com.developer.common.success.SuccessCode;
import com.developer.noti.command.application.dto.NotiCommentCreateDTO;
import com.developer.noti.command.application.service.NotiCommandService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/noti")
@RequiredArgsConstructor
public class NotiCommandController {

    private final NotiCommandService notiCommandService;

    // 알림 읽음처리
    @GetMapping("/read/{notiCode}")
    public ResponseEntity<SuccessCode> readNoti(@PathVariable(name = "notiCode") Long notiCode) {
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        notiCommandService.readNoti(currentUserCode, notiCode);

        return ResponseEntity.ok(SuccessCode.NOTI_READ_OK);
    }

    // 알림 삭제처리
    @DeleteMapping("/delete/{notiCode}")
    public ResponseEntity<SuccessCode> deleteNoti(@PathVariable(name = "notiCode") Long notiCode) {
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

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
