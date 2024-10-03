package com.developer.msg.command.application.controller;

import com.developer.client.UserServiceClient;
import com.developer.common.success.SuccessCode;
import com.developer.msg.command.application.dto.MessageRequestDTO;
import com.developer.msg.command.application.service.MessageCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/msg")
@Slf4j
public class MessageCommandController {

    private final MessageCommandService messageCommandService;
    private final UserServiceClient userServiceClient;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody MessageRequestDTO messageRequestDTO) {

        log.warn("호출 성공");
        Long loginUser = userServiceClient.responseUserCode();

        Long msgCode = messageCommandService.sendMessage(messageRequestDTO, loginUser);

        return ResponseEntity.created(URI.create("/msg/send" + msgCode)).build();
    }

    @PutMapping("/check/{msgCode}")
    public ResponseEntity<SuccessCode> updateReadStatusMessage(@PathVariable Long msgCode) {
        Long loginUser = userServiceClient.responseUserCode();

        messageCommandService.updateReadStatus(msgCode, loginUser);

        return ResponseEntity.ok(SuccessCode.MESSAGE_UPDATE_OK);
    }

    @DeleteMapping("/sender/{msgCode}")
    public ResponseEntity<SuccessCode> deleteSentMessage(@PathVariable Long msgCode) {
        Long loginUser = userServiceClient.responseUserCode();

        messageCommandService.deleteSentMessage(msgCode, loginUser);

        return ResponseEntity.ok(SuccessCode.MESSAGE_DELETE_OK);
    }

    @DeleteMapping("/receiver/{msgCode}")
    public ResponseEntity<SuccessCode> deleteReceivedMessage(@PathVariable Long msgCode) {
        Long loginUser = userServiceClient.responseUserCode();

        messageCommandService.deleteReceivedMessage(msgCode, loginUser);

        return ResponseEntity.ok(SuccessCode.MESSAGE_DELETE_OK);
    }
}
