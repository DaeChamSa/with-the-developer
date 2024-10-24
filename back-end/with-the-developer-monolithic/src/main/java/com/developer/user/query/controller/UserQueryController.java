package com.developer.user.query.controller;

import com.developer.user.command.domain.repository.BannedUserRepository;
import com.developer.user.query.dto.CheckCodeDTO;
import com.developer.user.query.dto.FindIdDTO;
import com.developer.user.query.dto.ResponseUserDTO;
import com.developer.user.query.service.EmailQueryService;
import com.developer.user.query.service.UserQueryService;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Tag(name = "user", description = "사용자 API")
@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserQueryController {

    private final UserQueryService userService;
    private final EmailQueryService emailService;
    private final BannedUserRepository bannedUserRepository;

    // 회원 정보 조회 (토큰 방식)
    @GetMapping
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
    public ResponseEntity<?> checkEmail(@RequestBody @Valid CheckCodeDTO checkCodeDTO){

        // sendDate가 null이면 현재 시간 넣기
        if (checkCodeDTO.getSendDate() == null){
            checkCodeDTO.setSendDate(new Date());
        }
        
        emailService.checkCode(checkCodeDTO);

        return ResponseEntity.ok().build();
    }

    // 아이디 중복 확인
    @GetMapping("/check-id/{userId}")
    public ResponseEntity<Boolean> checkId(@PathVariable(name = "userId") @Email String userId){

        boolean isUnique = userService.checkId(userId);
        return ResponseEntity.ok(isUnique);
    }

    // 닉네임 중복 확인
    @GetMapping("/check-nick/{nick}")
    public ResponseEntity<Boolean> checkNick(@PathVariable String nick){

        boolean isUnique = userService.checkNick(nick);
        return ResponseEntity.ok(isUnique);
    }

    // 핸드폰 번호 중복 확인
    @GetMapping("/check-phone/{phone}")
    public ResponseEntity<Boolean> checkPhone(@PathVariable String phone){

        boolean isUnique = userService.checkPhone(phone);
        return ResponseEntity.ok(isUnique);
    }

    // 아이디 찾기 (코드 검증)
    @PostMapping("/find-id")
    @Operation(summary = "아이디 찾기", description = "검증을 통해 아이디를 찾습니다.")
    public ResponseEntity<String> findId(@RequestBody @Valid FindIdDTO findIdDTO){

        log.info("findIdDTO {}", findIdDTO);
        String id = userService.findId(findIdDTO);

        return ResponseEntity.ok(id);
    }

    // userCode로 user 찾기
    @GetMapping("/{userCode}")
    public ResponseEntity<ResponseUserDTO> findByUserCode(@PathVariable("userCode") Long userCode){
        ResponseUserDTO responseUserDTO = userService.findByUserCode(userCode);
        return ResponseEntity.ok(responseUserDTO);
    }

    // userId로 user 찾기
    @GetMapping("/byUserId")
    public ResponseEntity<ResponseUserDTO> findByUserID(@RequestParam("userId") String userId){
        ResponseUserDTO responseUserDTO = userService.findByUserID(userId);
        return ResponseEntity.ok(responseUserDTO);
    }

}
