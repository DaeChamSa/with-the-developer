package com.example.withthedevelopergateway.filter;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationHeaderFilter.class);
    Environment environment;

    public AuthorizationHeaderFilter(Environment environment) {

        super(Config.class);
        this.environment = environment;
    }

    /* Gateway Filter를 반환하며 exchange와 chain 객체를 사용하여 요청과 응답 처리 및 다음 필터 실행 */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {   // exchange는 request와 response가 캡슐화 된 하나의 객체
            environment.getProperty("jwt.secret");
            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Authorization header is missing");
            }

            String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if(!bearerToken.startsWith("Bearer ")) {
                return onError(exchange, "Invalid or missing Bearer token");
            }

            String jwt = bearerToken.replace("Bearer ", "");

            if(!isJwtValid(jwt)) {
                return onError(exchange, "Invalid or expired JWT token");
            }

            return chain.filter(exchange);
        };
    }

    private boolean isJwtValid(String jwt) {
        boolean valid = true;
        String subject = null;
        try {
            subject = Jwts.parser()
                    .setSigningKey(environment.getProperty("jwt.secret"))
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            valid = false;
            e.printStackTrace();
        }

        if(subject == null || subject.isEmpty()) {
            valid = false;
        }

        return valid;
    }

    /* Mono는 0 or 1의 객체를 비동기적으로 처리할 때 사용 (비동기 작업의 성공 or 실패를 나타내기 위한 반환 타입) */
    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        /* 상태 코드만 설정해서 에러 발생 시 바로 응답 처리 할 경우 */
//        return response.setComplete();

        /* 에러 메세지도 담아서 응답 처리 할 경우 */
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String responseBody = "{\"error\":\"" + errorMessage + "\"}";

        DataBufferFactory bufferFactory = response.bufferFactory();
        DataBuffer buffer = bufferFactory.wrap(responseBody.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    public static class Config {}
}
