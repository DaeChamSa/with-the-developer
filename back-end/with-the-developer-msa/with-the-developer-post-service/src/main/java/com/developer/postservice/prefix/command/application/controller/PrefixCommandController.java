package com.developer.postservice.prefix.command.application.controller;

import com.developer.postservice.client.UserServiceClient;
import com.developer.postservice.common.success.SuccessCode;
import com.developer.postservice.prefix.command.application.dto.PrefixCreateDTO;
import com.developer.postservice.prefix.command.application.service.PrefixCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "prefix", description = "수식어 API")
@RestController
@RequestMapping("/prefix")
@RequiredArgsConstructor
public class PrefixCommandController {

    private final PrefixCommandService prefixCommandService;
    private final UserServiceClient userServiceClient;

    // 수식어 생성
    @PostMapping
    @Operation(summary = "수식어 생성", description = "DBTI와 희망직무를 결합하여 수식어를 생성합니다.")
    public ResponseEntity<SuccessCode> prefixCreate(@RequestBody PrefixCreateDTO prefixCreateDTO){

        Long currentUserCode = userServiceClient.getCurrentUserCode();

        prefixCommandService.prefixCreate(currentUserCode, prefixCreateDTO);

        return ResponseEntity.ok(SuccessCode.PREFIX_CREATE_OK);
    }
}
