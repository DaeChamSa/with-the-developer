package com.developer.dbti.query.controller;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.dbti.command.domain.aggregate.DbtiRole;
import com.developer.dbti.query.dto.ResponseDbtiDTO;
import com.developer.dbti.query.service.DbtiQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dbti")
@RequiredArgsConstructor
public class DbtiQueryController {

    private final DbtiQueryService dbtiQueryService;

    @PostMapping("/result")
    public ResponseEntity<List<String>> dbtiResult(@RequestBody String dbtiResult) {

        List<String> roles = dbtiQueryService.dbtiResult(dbtiResult);

        return ResponseEntity.ok(roles);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseDbtiDTO>> findAll(){
        List<ResponseDbtiDTO> all = dbtiQueryService.findAll();

        return ResponseEntity.ok(all);
    }

}
