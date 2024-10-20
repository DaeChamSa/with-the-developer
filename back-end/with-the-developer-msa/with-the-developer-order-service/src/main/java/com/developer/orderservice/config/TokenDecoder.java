package com.developer.orderservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class TokenDecoder {

    private final Key key;

    public TokenDecoder(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Long getUserCodeFromToken(String token) {
        // JWT 토큰에서 "Bearer " 접두사 제거
        String jwt = token.substring(7);

        // JWT 파싱 및 검증
        Claims claims = Jwts.parser()
                .setSigningKey(key) // 서명키로 JWT 토큰 검증
                .parseClaimsJws(jwt)
                .getBody();

        // 클레임에서 userCode 추출
        return Long.parseLong(claims.get("userCode").toString());
    }

    public String getUserIdFromToken(String token) {
        String jwt = token.substring(7);

        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody();

        return claims.getSubject();
    }
}