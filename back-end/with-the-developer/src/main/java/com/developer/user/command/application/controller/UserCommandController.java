package com.developer.user.command.application.controller;

import com.developer.common.success.SuccessCode;
import com.developer.common.jwt.TokenDTO;
import com.developer.user.command.application.dto.*;
import com.developer.user.command.application.service.EmailCommandService;
import com.developer.user.command.application.service.UserCommandService;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.user.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

@Tag(name = "user", description = "사용자 API")
@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserCommandController {

    private final UserCommandService userService;
    private final EmailCommandService emailService;

    // 회원가입
    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "회원정보를 통해 회원가입을 합니다.")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDTO userDTO) throws ParseException {

        Long userCode = userService.registerUser(userDTO);

        return ResponseEntity.ok("회원가입 성공");
    }

    // 이메일 체킹 (인증코드 날리기)
    @PostMapping("/send-code")
    @Operation(summary = "이메일 인증코드 전송", description = "회원가입을 위해 필요한 이메일 인증코드를 전송합니다.")
    public ResponseEntity<?> sendEmail(@RequestBody SendEmailDTO sendEmailDTO) throws MessagingException, UnsupportedEncodingException {
        String code = emailService.sendEmail(sendEmailDTO);

        log.info("이메일 인증코드 전송 {}", code);

        return ResponseEntity.ok().build();
    }

    // 로그인
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "회원으로 등록되어 있으면 로그인을 할 수 있습니다.")
    public ResponseEntity<SuccessCode> loginUser(@RequestBody LoginUserDTO userDTO){

        TokenDTO tokenDTO = userService.loginUser(userDTO);

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();

        // 헤더에 AccessToken
        headers.add("Authorization", "Bearer " + tokenDTO.getAccessToken());
        headers.add("Refresh-Token", tokenDTO.getRefreshToken());

        return ResponseEntity.ok()
                .headers(headers)
                .body(SuccessCode.USER_LOGIN_OK);
    }
    
    // 로그아웃
    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그인 되어 있는 회원이 로그아웃합니다.")
    public ResponseEntity<SuccessCode> logoutUser(){

        String userId = SecurityUtil.getCurrentUserId();
        log.info("userId {}", userId);

        if (!userId.isEmpty()) {
            String currentAccessToken = SecurityUtil.getCurrentAccessToken();
            userService.logoutUser(userId, currentAccessToken);
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok(SuccessCode.USER_LOGOUT_OK);
        } else {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }

    }

    // 회원 정보 수정
    @PutMapping("/update")
    @Operation(summary = "회원 정보 수정", description = "등록되어 있는 회원 정보를 수정합니다.")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDTO updateUserDTO) throws ParseException {

        String currentUserId = SecurityUtil.getCurrentUserId();

        if(currentUserId != null){
            userService.updateUser(currentUserId, updateUserDTO);
            return ResponseEntity.ok("정보 수정 성공");
        } else {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }
    }

    // 회원 탈퇴
    @DeleteMapping("/delete")
    @Operation(summary = "회원 탈퇴", description = "로그인되어 있는 회원은 탈퇴를 할 수 있다.")
    public ResponseEntity<String> deleteUser(){

        String currentUserId = SecurityUtil.getCurrentUserId();

        if (currentUserId != null){

            userService.deleteUser(currentUserId);

            return ResponseEntity.ok("탈퇴 성공");
        } else {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }
    }

    // AccessToken 재발급 (AccessToken만료)
    @PostMapping("/reissue/access")
    @Operation(summary = "Access Token 재발급", description = "Access Token을 재발급합니다.")
    public ResponseEntity<?> reissueAccessToken(@RequestParam(name = "refreshToken") String refreshToken){

        String accessToken = userService.reissue(refreshToken);

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();

        // 헤더에 AccessToken
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Refresh-Token", refreshToken);

        return ResponseEntity.ok()
                .headers(headers)
                .body(SuccessCode.ACCESS_TOKEN_REISSUE_OK);
    }

    // 알림 수신 여부 허용
    @PostMapping("/res-noti/accept")
    @Operation(summary = "알림 수신 허용", description = "알림 수신 여부를 허용으로 변경합니다.")
    public ResponseEntity<SuccessCode> notiAccept(){
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        userService.notiAccept(currentUserCode);

        return ResponseEntity.ok(SuccessCode.NOTI_ACCEPT_OK);
    }

    // 알림 수신 여부 허용
    @PostMapping("/res-noti/reject")
    @Operation(summary = "알림 수신 해제", description = "알림 수신 여부를 해제로 변경합니다.")
    public ResponseEntity<SuccessCode> notiReject(){
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        userService.notiReject(currentUserCode);

        return ResponseEntity.ok(SuccessCode.NOTI_REJECT_OK);
    }

    // 비밀번호 재설정
    @PostMapping("/reset-pw")
    @Operation(summary = "비밀번호 재설정", description = "이메일 인증을 통해 비밀번호를 재설정 합니다.")
    public ResponseEntity<SuccessCode> pwResseting(@RequestBody PwResettingDTO pwResettingDTO){
        userService.pwResetting(pwResettingDTO);

        return ResponseEntity.ok(SuccessCode.PW_RESETTING_OK);
    }
}
