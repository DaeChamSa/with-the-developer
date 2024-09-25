package com.developer.dbti.command.application.controller;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.dbti.command.application.dto.DbtiAddDTO;
import com.developer.dbti.command.application.service.DbtiCommandService;
import com.developer.dbti.command.domain.aggregate.DbtiRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/dbti")
@RequiredArgsConstructor
public class DbtiCommandController {

    private final DbtiCommandService dbtiCommandService;

    @PostMapping("/add")
    public ResponseEntity<?> addDbti(@RequestBody DbtiAddDTO dbtiAddDTO){

        if (!DbtiRole.contains(dbtiAddDTO.getDbtiRole())){
            log.info("일치하지 않는 dbtiRole {}", dbtiAddDTO.getDbtiRole());
            throw new CustomException(ErrorCode.NOT_MATCH_DBTI_ROLE);
        }

        dbtiCommandService.addDbti(dbtiAddDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{dbtiId}")
    public ResponseEntity<?> deleteDbti(@PathVariable Long dbtiId){

        dbtiCommandService.deleteDbti(dbtiId);

        return ResponseEntity.ok().build();
    }

}
