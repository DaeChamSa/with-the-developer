package com.developer.user.command.application.controller;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.common.jwt.ReissueTokenDTO;
import com.developer.common.jwt.TokenDTO;
import com.developer.common.success.SuccessCode;
import com.developer.user.command.application.dto.*;
import com.developer.user.command.application.service.EmailCommandService;
import com.developer.user.command.application.service.UserCommandService;
import com.developer.user.security.SecurityUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserCommandController {

    private final UserCommandService userService;
    private final EmailCommandService emailService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterUserDTO userDTO) {

        userService.registerUser(userDTO);

        return ResponseEntity.ok(SuccessCode.USER_REGISTER_OK.getMessage());
    }

    // 이메일 체킹 (인증코드 날리기)
    @PostMapping("/send-code")
    public ResponseEntity<?> sendEmail(@RequestBody @Valid SendEmailDTO sendEmailDTO) throws MessagingException {
        String code = emailService.sendEmail(sendEmailDTO);

        log.info("이메일 인증코드 전송 {}", code);

        return ResponseEntity.ok().build();
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<SuccessCode> loginUser(@RequestBody @Valid LoginUserDTO userDTO){

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
    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody @Valid UpdateUserDTO updateUserDTO) {

        String currentUserId = SecurityUtil.getCurrentUserId();

        if(currentUserId != null){
            userService.updateUser(currentUserId, updateUserDTO);
            return ResponseEntity.ok("정보 수정 성공");
        } else {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<String> deleteUser(){

        String currentUserId = SecurityUtil.getCurrentUserId();

        if (currentUserId != null){

            userService.deleteUser(currentUserId);

            return ResponseEntity.ok("탈퇴 성공");
        } else {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }
    }

    // (Access, Refresh) Token 재발급
    @PostMapping("/refresh")
    public ResponseEntity<?> reissueAccessToken(@RequestHeader(name = "Refresh-Token") String refreshToken){

        ReissueTokenDTO newToken = userService.reissue(refreshToken);

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();

        // 헤더에 AccessToken
        headers.add("Authorization", "Bearer " + newToken.getAccessToken());
        headers.add("Refresh-Token", newToken.getRefreshToken());

        return ResponseEntity.ok()
                .headers(headers)
                .body(SuccessCode.ACCESS_TOKEN_REISSUE_OK);
    }

    // 알림 수신 여부 허용
    @PostMapping("/res-noti/accept")
    public ResponseEntity<SuccessCode> notiAccept(){
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        userService.notiAccept(currentUserCode);

        return ResponseEntity.ok(SuccessCode.NOTI_ACCEPT_OK);
    }

    // 알림 수신 여부 허용
    @PostMapping("/res-noti/reject")
    public ResponseEntity<SuccessCode> notiReject(){
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        userService.notiReject(currentUserCode);

        return ResponseEntity.ok(SuccessCode.NOTI_REJECT_OK);
    }

    // 비밀번호 재설정
    @PostMapping("/reset-pw")
    public ResponseEntity<SuccessCode> pwResseting(@RequestBody @Valid PwResettingDTO pwResettingDTO){
        userService.pwResetting(pwResettingDTO);

        return ResponseEntity.ok(SuccessCode.PW_RESETTING_OK);
    }

    // 로그인 중인 유저 코드 반환
    @GetMapping("/userCode")
    public Long responseUserCode(){
        return SecurityUtil.getCurrentUserCode();
    }

    // 유저 존재 유무 확인
    @GetMapping("/user-check/{userCode}")
    public ResponseEntity<Boolean> userCheck(@PathVariable Long userCode){
        return ResponseEntity.ok(userService.existsUserByUserCode(userCode));
    }

    // 로그인 중인 유저 알림 수신 여부 반환
    @GetMapping("/user-isResNoti/{userCode}")
    public Boolean userIsResNoti(@PathVariable Long userCode){
        return userService.checkUserIsResNoti(userCode);
    }

    @GetMapping("/user-id-check/{userId}")
    public ResponseEntity<Boolean> userIdCheck(@PathVariable String userId) {
        return ResponseEntity.ok(userService.existsUserByUserId(userId));
    }
}
