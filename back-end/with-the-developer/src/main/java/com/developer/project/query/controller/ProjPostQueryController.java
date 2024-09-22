package com.developer.project.query.controller;

import com.developer.project.query.dto.ProjPostResponseDTO;
import com.developer.project.query.service.ProjPostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/proj")
@RestController
public class ProjPostQueryController {

    private final ProjPostQueryService projPostQueryService;

    @GetMapping("/post")
    public ResponseEntity<List<ProjPostResponseDTO>> readAll(@RequestParam(defaultValue = "1") Integer page) {
        List<ProjPostResponseDTO> projPostResponseDTOList = projPostQueryService.readAll(page);

        return ResponseEntity.ok(projPostResponseDTOList);
    }

    @GetMapping("/post/{projPostCode}")
    public ResponseEntity<ProjPostResponseDTO> readByCode(@PathVariable Long projPostCode) {
        ProjPostResponseDTO projPostResponseDTO = projPostQueryService.readByCode(projPostCode);

        return ResponseEntity.ok(projPostResponseDTO);
    }
}
