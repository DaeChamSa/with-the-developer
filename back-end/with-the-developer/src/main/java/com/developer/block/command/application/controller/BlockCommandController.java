package com.developer.block.command.application.controller;

import com.developer.block.command.application.service.BlockCommandService;
import com.developer.common.success.SuccessCode;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blocks")
public class BlockCommandController {

    private final BlockCommandService blockCommandService;

    @PostMapping("/block")
    public ResponseEntity<SuccessCode> blockUser(@RequestBody Long blockedCode) {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        blockCommandService.blockUser(loginUser, blockedCode);

        return ResponseEntity.ok(SuccessCode.BLOCK_OK);
    }

    @PostMapping("/unblock")
    public ResponseEntity<SuccessCode> unblockUser(@RequestBody Long blockedCode) {
        Long loginUser = SecurityUtil.getCurrentUserCode();

        blockCommandService.unblockUser(loginUser, blockedCode);

        return ResponseEntity.ok(SuccessCode.UNBLOCK_OK);
    }
}
