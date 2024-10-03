package com.developer.block.command.application.controller;

import com.developer.block.command.application.service.BlockCommandService;
import com.developer.common.success.SuccessCode;
import com.developer.config.TokenDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blocks")
public class BlockCommandController {

    private final BlockCommandService blockCommandService;
    private final TokenDecoder tokenDecoder;

    @PostMapping("/block")
    public ResponseEntity<SuccessCode> blockUser(@RequestHeader("Authorization") String token, @RequestBody Long blockedCode) {
        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        blockCommandService.blockUser(loginUser, blockedCode);

        return ResponseEntity.ok(SuccessCode.BLOCK_OK);
    }

    @PostMapping("/unblock")
    public ResponseEntity<SuccessCode> unblockUser(@RequestHeader("Authorization") String token, @RequestBody Long blockedCode) {
        Long loginUser = tokenDecoder.getUserCodeFromToken(token);

        blockCommandService.unblockUser(loginUser, blockedCode);

        return ResponseEntity.ok(SuccessCode.UNBLOCK_OK);
    }
}
