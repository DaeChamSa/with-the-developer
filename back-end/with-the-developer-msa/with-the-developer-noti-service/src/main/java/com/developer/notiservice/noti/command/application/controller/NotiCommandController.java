package com.developer.notiservice.noti.command.application.controller;

import com.developer.notiservice.client.UserServiceClient;
import com.developer.notiservice.common.success.SuccessCode;
import com.developer.notiservice.noti.command.application.dto.NotiCommentCreateDTO;
import com.developer.notiservice.noti.command.application.dto.NotiRecruitCreateDTO;
import com.developer.notiservice.noti.command.application.dto.NotiMsgCreateDTO;
import com.developer.notiservice.noti.command.application.service.NotiCommandService;
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
    private final UserServiceClient userServiceClient;

    // 알림 읽음처리
    @PutMapping("/{notiCode}")
    @Operation(summary = "알림 읽음 처리", description = "알림을 읽은 경우 읽음으로 상태를 변경합니다.")
    public ResponseEntity<SuccessCode> readNoti(@PathVariable(name = "notiCode") Long notiCode) {
        Long currentUserCode = userServiceClient.getCurrentUserCode();

        notiCommandService.readNoti(currentUserCode, notiCode);

        return ResponseEntity.ok(SuccessCode.NOTI_READ_OK);
    }

    // 알림 삭제처리
    @DeleteMapping("/{notiCode}")
    @Operation(summary = "알림 삭제", description = "받은 알림을 삭제할 수 있습니다.")
    public ResponseEntity<SuccessCode> deleteNoti(@PathVariable(name = "notiCode") Long notiCode) {
        Long currentUserCode = userServiceClient.getCurrentUserCode();

        notiCommandService.deleteNoti(currentUserCode, notiCode);

        return ResponseEntity.ok(SuccessCode.NOTI_DELETE_OK);
    }

    // 테스트하기 위해 만들어놓음
    @PostMapping
    @Operation(summary = "알림 생성", description = "알림을 생성한다.")
    public ResponseEntity<?> createNoti(@RequestBody NotiCommentCreateDTO notiCommentCreateDTO) {
        notiCommandService.addCommentEvent(notiCommentCreateDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/msg")
    public ResponseEntity<Void> addMsgEvent(@RequestBody NotiMsgCreateDTO notiMsgCreateDTO) {
        notiCommandService.addMsgEvent(notiMsgCreateDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recruit/appr")
    public ResponseEntity<Void> addAcceptEvent(@RequestBody NotiRecruitCreateDTO notiRecruitcreateDTO) {
        notiCommandService.addAcceptEvent(notiRecruitcreateDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recruit/reject")
    public ResponseEntity<Void> addRejectEvent(@RequestBody NotiRecruitCreateDTO notiRecruitcreateDTO) {
        notiCommandService.addRejectEvent(notiRecruitcreateDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/comment")
    public ResponseEntity<Void> addCommentEvent(@RequestBody NotiCommentCreateDTO notiCommentCreateDTO) {
        notiCommandService.addCommentEvent(notiCommentCreateDTO);
        return ResponseEntity.ok().build();
    }
}
