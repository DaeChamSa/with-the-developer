package com.developer.user.query.controller;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.user.command.dto.ResponseUserDTO;
import com.developer.user.command.dto.SessionSaveDTO;
import com.developer.user.query.dto.CheckCodeDTO;
import com.developer.user.query.dto.FindIdDTO;
import com.developer.user.query.service.EmailService;
import com.developer.user.query.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController("userQueryController")
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserQueryController {

    private final UserService userService;
    private final EmailService emailService;

    // 회원 정보 조회 (세션 방식)
    @GetMapping("/detail")
    public ResponseEntity<ResponseUserDTO> userDetail(HttpServletRequest httpServletRequest){

        // 세션이 있으면 생성 안하기
        HttpSession session = httpServletRequest.getSession(false);

        if (session != null){
            SessionSaveDTO userId = (SessionSaveDTO)session.getAttribute("user");
            log.info("userId 존재 {}", userId);
            ResponseUserDTO responseUserDTO = userService.findByUserID(userId.getUserId());

            return ResponseEntity.ok(responseUserDTO);
        } else {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }
    }

    // 사용자 상태별 User 객체 찾기
    @GetMapping("/status")
    public ResponseEntity<List<ResponseUserDTO>> findUserStatus(@RequestParam(name = "userStatus") String userStatus,
                                                                HttpServletRequest httpServletRequest){

        log.info("사용자 상태별 조회 {}", userStatus);

        List<ResponseUserDTO> allByUserStatus = userService.findAllByUserStatus(userStatus);

        return ResponseEntity.ok(allByUserStatus);
    }

    // 신고 10회 이상 User 확인하기
    @GetMapping("/warning")
    public ResponseEntity<List<ResponseUserDTO>> findUserWarning(HttpServletRequest httpServletRequest){

        List<ResponseUserDTO> allByUserWarning = userService.findAllByUserWarning();

        log.info("사용자 경고 누적 10회 초과 조회 {}", allByUserWarning);

        return ResponseEntity.ok(allByUserWarning);
    }

    // 인증코드 확인
    @PostMapping("/check-code")
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
    public ResponseEntity<String> findId(@RequestBody FindIdDTO code){

        log.info("code {}", code);
        String id = emailService.findId(code);

        return ResponseEntity.ok("사용자 아이디 : " + id);
    }
}
