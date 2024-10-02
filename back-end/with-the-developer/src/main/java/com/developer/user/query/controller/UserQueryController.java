package com.developer.user.query.controller;

import com.developer.user.query.dto.ResponseUserDTO;
import com.developer.user.query.dto.CheckCodeDTO;
import com.developer.user.query.dto.FindIdDTO;
import com.developer.user.query.service.EmailQueryService;
import com.developer.user.query.service.UserQueryService;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "user", description = "사용자 API")
@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserQueryController {

    private final UserQueryService userService;
    private final EmailQueryService emailService;

    // 회원 정보 조회 (토큰 방식)
    @GetMapping("/detail")
    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
    public ResponseEntity<ResponseUserDTO> userDetail(){

        Long userCode = SecurityUtil.getCurrentUserCode();

        log.info("로그인 되어있는 userCode {}", userCode);
        ResponseUserDTO byUserCode = userService.findByUserCode(userCode);

        return ResponseEntity.ok(byUserCode);
    }

    // 인증코드 확인
    @PostMapping("/check-code")
    @Operation(summary = "인증코드 확인하기", description = "이메일 인증을 통해 본인 확인을 합니다.")
    public ResponseEntity<?> checkEmail(@RequestBody CheckCodeDTO checkCodeDTO){

        // sendDate가 null이면 현재 시간 넣기
        if (checkCodeDTO.getSendDate() == null){
            checkCodeDTO.setSendDate(new Date());
        }
        
        emailService.checkCode(checkCodeDTO);

        return ResponseEntity.ok().build();
    }

    // 아이디 찾기 (코드 검증)
    @PostMapping("/find-id")
    @Operation(summary = "아이디 찾기", description = "코드 검증을 통해 아이디를 찾습니다.")
    public ResponseEntity<String> findId(@RequestBody FindIdDTO code){

        log.info("code {}", code);
        String id = emailService.findId(code);

        return ResponseEntity.ok("사용자 아이디 : " + id);
    }
}
