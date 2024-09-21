package com.developer.user.command.controller;

import com.developer.user.command.dto.*;
import com.developer.user.command.service.EmailCommandService;
import com.developer.user.command.service.UserCommandService;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.user.security.SecurityUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserCommandController {

    private final UserCommandService userService;
    private final EmailCommandService emailService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDTO userDTO) throws ParseException {

        userService.registerUser(userDTO);

        return ResponseEntity.ok("회원가입 성공");
    }

    // 이메일 체킹 (인증코드 날리기)
    @PostMapping("/send-code")
    public ResponseEntity<?> sendEmail(@RequestBody SendEmailDTO sendEmailDTO) throws MessagingException, UnsupportedEncodingException {
        String code = emailService.sendEmail(sendEmailDTO);

        log.info("이메일 인증코드 전송 {}", code);

        return ResponseEntity.ok().build();
    }

//    // 인증코드 확인
//    @PostMapping("/check-code")
//    public ResponseEntity<?> checkEmail(@RequestBody String code){
//        emailService.codeCheck(code);
//    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserDTO userDTO,
                                       HttpServletRequest httpServletRequest){

        SessionSaveDTO sessionSaveDTO = userService.loginUser(userDTO);

        // 기존 세션 삭제
        httpServletRequest.getSession().invalidate();
        // 세션 생성
        HttpSession session = httpServletRequest.getSession(true);
        // 세션에 userId 넣기
        session.setAttribute("user", sessionSaveDTO);
        session.setMaxInactiveInterval(3600);   // 한 시간 동안 세션 유지

        return ResponseEntity.ok("로그인 성공");
    }
    
    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest httpServletRequest){

        Long memberId = SecurityUtil.getCurrentUserCode();
        log.info("memberId {}", memberId);

        // 세션이 있으면 생성 안하기
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null){
            session.invalidate();
            return ResponseEntity.ok("로그아웃 성공");
        } else {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }
    }

    // 회원 정보 수정
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(HttpServletRequest httpServletRequest,
                                        @RequestBody UpdateUserDTO updateUserDTO) throws ParseException {

        // 세션이 있으면 생성 안하기
        HttpSession session = httpServletRequest.getSession(false);

        if (session != null){
            SessionSaveDTO sessionSaveDTO = (SessionSaveDTO)session.getAttribute("user");
            userService.updateUser(sessionSaveDTO.getUserId(), updateUserDTO);

            return ResponseEntity.ok("정보 수정 성공");
        } else {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }
    }

//    // 회원 정보 조회 (세션 방식)
//    @GetMapping("/detail")
//    public ResponseEntity<ResponseUserDTO> userDetail(HttpServletRequest httpServletRequest){
//
//        // 세션이 있으면 생성 안하기
//        HttpSession session = httpServletRequest.getSession(false);
//
//        if (session != null){
//            String userId = (String)session.getAttribute("userId");
//            ResponseUserDTO responseUserDTO = userService.userDetail(userId);
//
//            return ResponseEntity.ok(responseUserDTO);
//        } else {
//            throw new CustomException(ErrorCode.NEED_LOGIN);
//        }
//    }


    // 회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(HttpServletRequest httpServletRequest){

        // 세션이 있으면 생성 안하기
        HttpSession session = httpServletRequest.getSession(false);

        if (session != null){
            SessionSaveDTO sessionSaveDTO = (SessionSaveDTO)session.getAttribute("user");
            userService.deleteUser(sessionSaveDTO.getUserId());

            return ResponseEntity.ok("탈퇴 성공");
        } else {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }
    }

}
