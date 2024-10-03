package com.developer.block.query.controller;

import com.developer.block.query.dto.BlockResponseDTO;
import com.developer.block.query.service.BlockQueryService;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "block-user", description = "회원 차단 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/block")
public class BlockQueryController {

    private final BlockQueryService blockQueryService;

    @GetMapping
    @Operation(summary = "회원 차단 목록 조회", description = "차단한 회원 목록을 조회합니다.")
    public ResponseEntity<List<BlockResponseDTO>> findAll() {
        return ResponseEntity.ok(blockQueryService.findAll(SecurityUtil.getCurrentUserCode()));
    }
}
