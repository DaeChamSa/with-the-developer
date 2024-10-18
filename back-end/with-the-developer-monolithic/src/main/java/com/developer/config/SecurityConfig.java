package com.developer.config;


import com.developer.common.jwt.JwtFilter;
import com.developer.common.jwt.TokenProvider;
import com.developer.user.query.service.BlackListQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final BlackListQueryService blackListQueryService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        // permitall() => 접근 모두 허용
        // hasRole => 하나의 권한만 접근 가능하게 설정
        // hasAnyRole => 여러 개의 권한 접근 가능하게 설정 가능

        // CSRF 설정 Disable
        http.csrf(AbstractHttpConfigurer::disable)

                //HTTP Basic 인증 방식 disable
                .httpBasic(AbstractHttpConfigurer::disable)

                        .headers((headers) -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                // JWT 사용을 위해 세션 사용 X (상태 유지 X)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false))


                // 로그인, 회원가입 API는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**", "/**").permitAll()
                        .requestMatchers("/email/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated());

        // 로그인 로그아웃 비활성화 설정
        http.logout(AbstractHttpConfigurer::disable);
        http.formLogin((AbstractHttpConfigurer::disable));

        // JWT 필터 추가
        http.addFilterBefore(new JwtFilter(tokenProvider, blackListQueryService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
