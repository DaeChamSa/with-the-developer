package com.developer.common.module;

import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminAccountInitializer {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 일단 이걸로 관리자 계정 자동 생성

    @PostConstruct
    public void init() {

        // 관리자 계정 없으면 생성
        if (!userRepository.existsByUserId("admin")){

            // 관리자 계정 생성
            User user = new User("admin"
                    , passwordEncoder.encode("admin")
                    , "first_admin"
                    ,"admin1"
            );
            
            userRepository.save(user);
            log.info("관리자 계정 생성 완료");
        } else {
            log.info("관리자 계정 이미 존재");
        }
    }

}
