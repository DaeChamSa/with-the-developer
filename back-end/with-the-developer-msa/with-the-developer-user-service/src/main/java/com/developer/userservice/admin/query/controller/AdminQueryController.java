package com.developer.userservice.admin.query.controller;

import com.developer.userservice.admin.query.service.AdminQueryService;
import com.developer.userservice.user.query.dto.ResponseUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "admin", description = "관리자 기능 API")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminQueryController {
    private final AdminQueryService adminQueryService;


    // ========= 사용자 =========
    // 사용자 상태별 User 객체 찾기
    @GetMapping("/user/status")
    @Operation(summary = "사용자 상태별 회원 조회", description = "관리자가 회원 관리를 위해 사용자 상태별 회원을 조회합니다.")
    public ResponseEntity<List<ResponseUserDTO>> findUserStatus(@RequestParam(name = "userStatus") String userStatus){

        log.info("사용자 상태별 조회 {}", userStatus);

        List<ResponseUserDTO> allByUserStatus = adminQueryService.findAllByUserStatus(userStatus);

        return ResponseEntity.ok(allByUserStatus);
    }

    // 신고 10회 이상 User 확인하기
    @GetMapping("/user/warning")
    @Operation(summary = "신고 10회 이상 회원 조회", description = "신고가 10회 이상인 회원을 조회합니다.")
    public ResponseEntity<List<ResponseUserDTO>> findUserWarning(HttpServletRequest httpServletRequest){

        List<ResponseUserDTO> allByUserWarning = adminQueryService.findAllByUserWarning();

        log.info("사용자 경고 누적 10회 초과 조회 {}", allByUserWarning);

        return ResponseEntity.ok(allByUserWarning);
    }
}
