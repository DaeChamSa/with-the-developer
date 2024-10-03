package com.developer.block.query.controller;

import com.developer.block.query.dto.BlockResponseDTO;
import com.developer.block.query.service.BlockQueryService;
import com.developer.client.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/block")
public class BlockQueryController {

    private final BlockQueryService blockQueryService;
    private final UserServiceClient userServiceClient;

    @GetMapping
    public ResponseEntity<List<BlockResponseDTO>> findAll() {
        return ResponseEntity.ok(blockQueryService.findAll(userServiceClient.responseUserCode()));
    }
}
