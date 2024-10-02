package com.developer.dbti.command.application.controller;

import com.developer.common.success.SuccessCode;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.dbti.command.application.dto.DbtiAddDTO;
import com.developer.dbti.command.application.service.DbtiCommandService;
import com.developer.dbti.command.domain.aggregate.DbtiRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "dbti", description = "dbti API")
@Slf4j
@RestController
@RequestMapping("/dbti")
@RequiredArgsConstructor
public class DbtiCommandController {

    private final DbtiCommandService dbtiCommandService;

    @PostMapping("/add")
    @Operation(summary = "dbti 추가", description = "새로운 dbti를 추가합니다.")
    public ResponseEntity<SuccessCode> addDbti(@RequestBody DbtiAddDTO dbtiAddDTO){

        if (!DbtiRole.contains(dbtiAddDTO.getDbtiRole())){
            log.info("일치하지 않는 dbtiRole {}", dbtiAddDTO.getDbtiRole());
            throw new CustomException(ErrorCode.NOT_MATCH_DBTI_ROLE);
        }

        dbtiCommandService.addDbti(dbtiAddDTO);

        return ResponseEntity.ok(SuccessCode.DBTI_CREATE_OK);
    }

    @DeleteMapping("/delete/{dbtiId}")
    @Operation(summary = "dbti 삭제", description = "등록되어 있는 dbti를 삭제합니다.ㅏ")
    public ResponseEntity<SuccessCode> deleteDbti(@PathVariable Long dbtiId){

        dbtiCommandService.deleteDbti(dbtiId);

        return ResponseEntity.ok(SuccessCode.DBTI_DELETE_OK);
    }

}
