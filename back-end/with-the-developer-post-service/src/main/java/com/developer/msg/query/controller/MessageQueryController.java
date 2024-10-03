package com.developer.msg.query.controller;

import com.developer.config.TokenDecoder;
import com.developer.msg.query.dto.ReqMsgResponseDTO;
import com.developer.msg.query.dto.ResMsgResponseDTO;
import com.developer.msg.query.service.MessageQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/msg")
public class MessageQueryController {

    private final MessageQueryService messageQueryService;
    private final TokenDecoder tokenDecoder;

    @GetMapping("/read-req")
    public ResponseEntity<List<ReqMsgResponseDTO>> findAllReqMsg(@RequestHeader("Authorization") String token) {
        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        return ResponseEntity.ok(messageQueryService.findAllReqMsg(loginUser));
    }

    @GetMapping("/read-res")
    public ResponseEntity<List<ResMsgResponseDTO>> findAllResMsg(@RequestHeader("Authorization") String token) {
        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        return ResponseEntity.ok(messageQueryService.findAllResMsg(loginUser));
    }

    @GetMapping("/read-req/{msgCode}")
    public ResponseEntity<ReqMsgResponseDTO> findReqMsgByMsgCodeAndUserCode(@RequestHeader("Authorization") String token, @PathVariable Long msgCode) {
        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        return ResponseEntity.ok(messageQueryService.findReqMsgByMsgCodeAndUserCode(msgCode, loginUser));
    }

    @GetMapping("/read-res/{msgCode}")
    public ResponseEntity<ResMsgResponseDTO> findResMsgByMsgCodeAndUserCode(@RequestHeader("Authorization") String token, @PathVariable Long msgCode) {
        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        return ResponseEntity.ok(messageQueryService.findResMsgByMsgCodeAndUserCode(msgCode, loginUser));
    }

    @GetMapping("/read-res/unread")
    public ResponseEntity<List<ResMsgResponseDTO>> findUnreadResMsg(@RequestHeader("Authorization") String token) {
        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        return ResponseEntity.ok(messageQueryService.findAllUnReadResMsg(loginUser));
    }
}
