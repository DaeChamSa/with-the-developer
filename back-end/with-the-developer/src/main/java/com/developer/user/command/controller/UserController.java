package com.developer.user.command.controller;

import com.developer.user.command.dto.LoginUserDTO;
import com.developer.user.command.dto.RegisterUserDTO;
import com.developer.user.command.dto.ResponseUserDTO;
import com.developer.user.command.dto.UpdateUserDTO;
import com.developer.user.command.service.UserService;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDTO userDTO) throws ParseException {

        userService.registerUser(userDTO);

        return ResponseEntity.ok("회원가입 성공");
    }

    // 이메일 체킹 (인증코드 날리기)

    // 인증코드 확인

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserDTO userDTO,
                                       HttpServletRequest httpServletRequest){

        userService.loginUser(userDTO);

        // 기존 세션 삭제
        httpServletRequest.getSession().invalidate();
        // 세션 생성
        HttpSession session = httpServletRequest.getSession(true);
        // 세션에 userId 넣기
        session.setAttribute("userId", userDTO.getUserId());
        session.setMaxInactiveInterval(3600);   // 한 시간 동안 세션 유지

        return ResponseEntity.ok("로그인 성공");
    }
    
    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest httpServletRequest){

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
    @PostMapping("/update")
    public ResponseEntity<String> updateUser(HttpServletRequest httpServletRequest,
                                        @RequestBody UpdateUserDTO updateUserDTO) throws ParseException {

        // 세션이 있으면 생성 안하기
        HttpSession session = httpServletRequest.getSession(false);

        if (session != null){
            String userId = (String)session.getAttribute("userId");
            userService.updateUser(userId, updateUserDTO);

            return ResponseEntity.ok("정보 수정 성공");
        } else {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }
    }

    // 회원 정보 조회 (세션 방식)
    @GetMapping("/detail")
    public ResponseEntity<ResponseUserDTO> userDetail(HttpServletRequest httpServletRequest){

        // 세션이 있으면 생성 안하기
        HttpSession session = httpServletRequest.getSession(false);

        if (session != null){
            String userId = (String)session.getAttribute("userId");
            ResponseUserDTO responseUserDTO = userService.userDetail(userId);

            return ResponseEntity.ok(responseUserDTO);
        } else {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }
    }


    // 회원 탈퇴
    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(HttpServletRequest httpServletRequest){

        // 세션이 있으면 생성 안하기
        HttpSession session = httpServletRequest.getSession(false);

        if (session != null){
            String userId = (String)session.getAttribute("userId");
            userService.deleteUser(userId);

            return ResponseEntity.ok("탈퇴 성공");
        } else {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }
    }

}
