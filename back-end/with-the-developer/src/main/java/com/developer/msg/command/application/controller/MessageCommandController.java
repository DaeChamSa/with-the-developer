package com.developer.msg.command.application.controller;

import com.developer.common.success.SuccessCode;
import com.developer.msg.command.application.dto.MessageRequestDTO;
import com.developer.msg.command.application.service.MessageCommandService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/msg")
public class MessageCommandController {

    private final MessageCommandService messageCommandService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody MessageRequestDTO messageRequestDTO) {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        Long msgCode = messageCommandService.sendMessage(messageRequestDTO, loginUser);

        return ResponseEntity.created(URI.create("/msg/send" + msgCode)).build();
    }

    @PutMapping("/check/{msgCode}")
    public ResponseEntity<SuccessCode> updateReadStatusMessage(@PathVariable Long msgCode) {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        messageCommandService.updateReadStatus(msgCode, loginUser);

        return ResponseEntity.ok(SuccessCode.MESSAGE_UPDATE_OK);
    }

    @DeleteMapping("/sender/{msgCode}")
    public ResponseEntity<SuccessCode> deleteSentMessage(@PathVariable Long msgCode) {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        messageCommandService.deleteSentMessage(msgCode, loginUser);

        return ResponseEntity.ok(SuccessCode.MESSAGE_DELETE_OK);
    }

    @DeleteMapping("/receiver/{msgCode}")
    public ResponseEntity<SuccessCode> deleteReceivedMessage(@PathVariable Long msgCode) {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        messageCommandService.deleteReceivedMessage(msgCode, loginUser);

        return ResponseEntity.ok(SuccessCode.MESSAGE_DELETE_OK);
    }
}
