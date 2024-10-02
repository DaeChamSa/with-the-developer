package com.developer.msg.query.controller;

import com.developer.msg.query.dto.ReqMsgResponseDTO;
import com.developer.msg.query.dto.ResMsgResponseDTO;
import com.developer.msg.query.service.MessageQueryService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/msg")
public class MessageQueryController {

    private final MessageQueryService messageQueryService;

    @GetMapping("/read-req")
    public ResponseEntity<List<ReqMsgResponseDTO>> findAllReqMsg() {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        return ResponseEntity.ok(messageQueryService.findAllReqMsg(loginUser));
    }

    @GetMapping("/read-res")
    public ResponseEntity<List<ResMsgResponseDTO>> findAllResMsg() {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        return ResponseEntity.ok(messageQueryService.findAllResMsg(loginUser));
    }

    @GetMapping("/read-req/{msgCode}")
    public ResponseEntity<ReqMsgResponseDTO> findReqMsgByMsgCodeAndUserCode(@PathVariable Long msgCode) {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        return ResponseEntity.ok(messageQueryService.findReqMsgByMsgCodeAndUserCode(msgCode, loginUser));
    }

    @GetMapping("/read-res/{msgCode}")
    public ResponseEntity<ResMsgResponseDTO> findResMsgByMsgCodeAndUserCode(@PathVariable Long msgCode) {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        return ResponseEntity.ok(messageQueryService.findResMsgByMsgCodeAndUserCode(msgCode, loginUser));
    }

    @GetMapping("/read-res/unread")
    public ResponseEntity<List<ResMsgResponseDTO>> findUnreadResMsg() {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        return ResponseEntity.ok(messageQueryService.findAllUnReadResMsg(loginUser));
    }
}
