package com.developer.noti.command.application.controller;

import com.developer.common.success.SuccessCode;
import com.developer.noti.command.application.dto.NotiCommentCreateDTO;
import com.developer.noti.command.application.service.NotiCommandService;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "notification", description = "알림 API")
@RestController
@RequestMapping("/noti")
@RequiredArgsConstructor
public class NotiCommandController {

    private final NotiCommandService notiCommandService;

    // 알림 읽음처리
    @GetMapping("/read/{notiCode}")
    @Operation(summary = "알림 읽음 처리", description = "알림을 읽은 경우 읽음으로 상태를 변경합니다.")
    public ResponseEntity<SuccessCode> readNoti(@PathVariable(name = "notiCode") Long notiCode) {
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        notiCommandService.readNoti(currentUserCode, notiCode);

        return ResponseEntity.ok(SuccessCode.NOTI_READ_OK);
    }

    // 알림 삭제처리
    @DeleteMapping("/delete/{notiCode}")
    @Operation(summary = "알림 삭제", description = "받은 알림을 삭제할 수 있습니다.")
    public ResponseEntity<SuccessCode> deleteNoti(@PathVariable(name = "notiCode") Long notiCode) {
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        notiCommandService.deleteNoti(currentUserCode, notiCode);

        return ResponseEntity.ok(SuccessCode.NOTI_DELETE_OK);
    }

    // 테스트하기 위해 만들어놓음
    @PostMapping("/create")
    @Operation(summary = "발신 쪽지 상세 조회", description = "본인이 발신했던 쪽지의 상세 내용을 조회합니다.")
    public ResponseEntity<?> createNoti(@RequestBody NotiCommentCreateDTO notiCommentCreateDTO) {
        notiCommandService.addCommentEvent(notiCommentCreateDTO);

        return ResponseEntity.ok().build();
    }
}
