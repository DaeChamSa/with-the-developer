package com.developer.prefix.query.controller;

import com.developer.prefix.query.dto.MapperPrefixDTO;
import com.developer.prefix.query.dto.ResponsePrefixDTO;
import com.developer.prefix.query.service.PrefixQueryService;
import com.developer.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prefix")
@RequiredArgsConstructor
public class PrefixQueryController {

    private final PrefixQueryService prefixQueryService;

    // 본인 수식어 조회
    @GetMapping("/me")
    public ResponseEntity<ResponsePrefixDTO> findPrefixByUserCode(){
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        ResponsePrefixDTO byUserCode = prefixQueryService.findByUserCode(currentUserCode);

        return ResponseEntity.ok(byUserCode);
    }
}
