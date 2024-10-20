package com.developer.user.security;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        log.info("===== UserDetailsService.loadUserByUserName =====");
        log.info("userId {}", userId);

        Optional<User> byUserId = userRepository.findByUserId(userId);

        log.info("byUserId {}", byUserId);

        return byUserId.map(this::createUserDetails)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

    }

    // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(User user) {

        log.info("createUserDetails method User: {}", user);

        return new CustomUserDetails(
                user
        );
    }
}
