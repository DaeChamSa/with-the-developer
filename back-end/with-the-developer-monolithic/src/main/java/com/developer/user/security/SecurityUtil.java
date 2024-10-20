package com.developer.user.security;

import com.developer.user.command.application.dto.TokenSaveDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@NoArgsConstructor
public class SecurityUtil {


    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장


    private static TokenSaveDTO getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("SecurityUtil.getCurrentMemberId 호출 {}", authentication.getPrincipal().toString());

        if (authentication.getPrincipal() == null || authentication.getName() == null ||
                !(authentication instanceof UsernamePasswordAuthenticationToken)){
            throw new RuntimeException("Security Context 에 인증 정보가 없음");
        }

        return (TokenSaveDTO) authentication.getPrincipal();
    }

    // 사용자 코드 반환
    public static Long getCurrentUserCode(){

        return getAuthentication().getUserCode();
    }

    // 사용자 아이디 반환
    public static String getCurrentUserId(){

        return getAuthentication().getUserId();
    }

    // 액세스 토큰 반환
    public static String getCurrentAccessToken(){

        return getAuthentication().getAccessToken();
    }
}
