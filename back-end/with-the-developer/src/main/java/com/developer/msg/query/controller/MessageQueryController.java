package com.developer.msg.query.controller;

import com.developer.msg.query.dto.ReqMsgResponseDTO;
import com.developer.msg.query.dto.ResMsgResponseDTO;
import com.developer.msg.query.service.MessageQueryService;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "message", description = "쪽지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/msg")
public class MessageQueryController {

    private final MessageQueryService messageQueryService;

    @GetMapping("/read-req")
    @Operation(summary = "발신 쪽지 목록 조회", description = "본인이 발신했던 쪽지 목록을 조회합니다.")
    public ResponseEntity<List<ReqMsgResponseDTO>> findAllReqMsg() {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        return ResponseEntity.ok(messageQueryService.findAllReqMsg(loginUser));
    }

    @GetMapping("/read-res")
    @Operation(summary = "수신 쪽지 목록 조회", description = "본인이 수신했던 쪽지 목록을 조회합니다.")
    public ResponseEntity<List<ResMsgResponseDTO>> findAllResMsg() {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        return ResponseEntity.ok(messageQueryService.findAllResMsg(loginUser));
    }

    @GetMapping("/read-req/{msgCode}")
    @Operation(summary = "발신 쪽지 상세 조회", description = "본인이 발신했던 쪽지의 상세 내용을 조회합니다.")
    public ResponseEntity<ReqMsgResponseDTO> findReqMsgByMsgCodeAndUserCode(@PathVariable Long msgCode) {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        return ResponseEntity.ok(messageQueryService.findReqMsgByMsgCodeAndUserCode(msgCode, loginUser));
    }

    @GetMapping("/read-res/{msgCode}")
    @Operation(summary = "수신 쪽지 상세 조회", description = "본인이 수신했던 쪽지의 상세 내용을 조회합니다.")
    public ResponseEntity<ResMsgResponseDTO> findResMsgByMsgCodeAndUserCode(@PathVariable Long msgCode) {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        return ResponseEntity.ok(messageQueryService.findResMsgByMsgCodeAndUserCode(msgCode, loginUser));
    }

    @GetMapping("/read-res/unread")
    @Operation(summary = "읽지 않은 쪽지 조회", description = "수신한 쪽지 중 읽지 않은 쪽지 목록을 조회합니다.")
    public ResponseEntity<List<ResMsgResponseDTO>> findUnreadResMsg() {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        return ResponseEntity.ok(messageQueryService.findAllUnReadResMsg(loginUser));
    }
}
