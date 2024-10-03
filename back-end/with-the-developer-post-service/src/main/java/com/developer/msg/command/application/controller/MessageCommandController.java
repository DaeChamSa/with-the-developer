package com.developer.msg.command.application.controller;

import com.developer.common.success.SuccessCode;
import com.developer.config.TokenDecoder;
import com.developer.msg.command.application.dto.MessageRequestDTO;
import com.developer.msg.command.application.service.MessageCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/msg")
public class MessageCommandController {

    private final MessageCommandService messageCommandService;
    private final TokenDecoder tokenDecoder;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestHeader("Authorization") String token, @RequestBody MessageRequestDTO messageRequestDTO) {
        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        Long msgCode = messageCommandService.sendMessage(messageRequestDTO, loginUser);

        return ResponseEntity.created(URI.create("/msg/send" + msgCode)).build();
    }

    @PutMapping("/check/{msgCode}")
    public ResponseEntity<SuccessCode> updateReadStatusMessage(@RequestHeader("Authorization") String token, @PathVariable Long msgCode) {
        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        messageCommandService.updateReadStatus(msgCode, loginUser);

        return ResponseEntity.ok(SuccessCode.MESSAGE_UPDATE_OK);
    }

    @DeleteMapping("/sender/{msgCode}")
    public ResponseEntity<SuccessCode> deleteSentMessage(@RequestHeader("Authorization") String token, @PathVariable Long msgCode) {
        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        messageCommandService.deleteSentMessage(msgCode, loginUser);

        return ResponseEntity.ok(SuccessCode.MESSAGE_DELETE_OK);
    }

    @DeleteMapping("/receiver/{msgCode}")
    public ResponseEntity<SuccessCode> deleteReceivedMessage(@RequestHeader("Authorization") String token, @PathVariable Long msgCode) {
        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        messageCommandService.deleteReceivedMessage(msgCode, loginUser);

        return ResponseEntity.ok(SuccessCode.MESSAGE_DELETE_OK);
    }
}
