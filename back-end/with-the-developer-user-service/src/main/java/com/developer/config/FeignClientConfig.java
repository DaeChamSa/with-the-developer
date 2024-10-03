package com.developer.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {

            @Override
            public void apply(RequestTemplate requestTemplate) {

                /* 현재 요청의 Http Servlet Request 를 가져옴 */
                ServletRequestAttributes requestAttributes =
                        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if(requestAttributes != null) {

                    /* 현재 요청의 Authorization 헤더 추출 (Bearer 토큰) */
                    String authorizationHeader = requestAttributes
                            .getRequest()           // request 객체 추출
                            .getHeader(HttpHeaders.AUTHORIZATION);

                    if(authorizationHeader != null) {       // 토큰을 들고 왔다면

                        /* Feign client 요청에 "Authorization" 헤더 추가 */
                        requestTemplate.header(HttpHeaders.AUTHORIZATION, authorizationHeader);
                    }
                }
            }
        };
    }
}
