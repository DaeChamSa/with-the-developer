package com.developer.msg.command.application.controller;

import com.developer.common.success.SuccessCode;
import com.developer.msg.command.application.dto.MessageRequestDTO;
import com.developer.msg.command.application.service.MessageCommandService;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "message", description = "쪽지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/msg")
public class MessageCommandController {

    private final MessageCommandService messageCommandService;

    @PostMapping("/send")
    @Operation(summary = "쪽지 발신", description = "쪽지를 발신합니다.")
    public ResponseEntity<Void> sendMessage(@RequestBody MessageRequestDTO messageRequestDTO) {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        Long msgCode = messageCommandService.sendMessage(messageRequestDTO, loginUser);

        return ResponseEntity.created(URI.create("/msg/send" + msgCode)).build();
    }

    @PutMapping("/check/{msgCode}")
    @Operation(summary = "쪽지 읽음 여부 변경", description = "쪽지를 읽을 경우, 읽음 상태를 변경합니다.")
    public ResponseEntity<SuccessCode> updateReadStatusMessage(@PathVariable Long msgCode) {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        messageCommandService.updateReadStatus(msgCode, loginUser);

        return ResponseEntity.ok(SuccessCode.MESSAGE_UPDATE_OK);
    }

    @DeleteMapping("/sender/{msgCode}")
    @Operation(summary = "발신 쪽지 삭제", description = "본인이 발신했던 쪽지를 삭제합니다. 해당 변경은 본인에게만 표시됩니다.")
    public ResponseEntity<SuccessCode> deleteSentMessage(@PathVariable Long msgCode) {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        messageCommandService.deleteSentMessage(msgCode, loginUser);

        return ResponseEntity.ok(SuccessCode.MESSAGE_DELETE_OK);
    }

    @DeleteMapping("/receiver/{msgCode}")
    @Operation(summary = "수신 쪽지 삭제", description = "본인이 수신했던 쪽지를 삭제합니다. 해당 변경은 본인에게만 표시됩니다.")
    public ResponseEntity<SuccessCode> deleteReceivedMessage(@PathVariable Long msgCode) {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        messageCommandService.deleteReceivedMessage(msgCode, loginUser);

        return ResponseEntity.ok(SuccessCode.MESSAGE_DELETE_OK);
    }
}
