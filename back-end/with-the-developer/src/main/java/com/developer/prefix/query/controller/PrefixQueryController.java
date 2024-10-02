package com.developer.prefix.query.controller;

import com.developer.prefix.query.dto.MapperPrefixDTO;
import com.developer.prefix.query.dto.ResponsePrefixDTO;
import com.developer.prefix.query.service.PrefixQueryService;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "prefix", description = "수식어 API")
@RestController
@RequestMapping("/prefix")
@RequiredArgsConstructor
public class PrefixQueryController {

    private final PrefixQueryService prefixQueryService;

    // 본인 수식어 조회
    @GetMapping("/me")
    @Operation(summary = "본인 수식어 조회", description = "본인의 수식어를 조회합니다.")
    public ResponseEntity<ResponsePrefixDTO> findPrefixByUserCode(){
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        ResponsePrefixDTO byUserCode = prefixQueryService.findByUserCode(currentUserCode);

        return ResponseEntity.ok(byUserCode);
    }
}
