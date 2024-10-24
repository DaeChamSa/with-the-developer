package com.developer.user.command.domain.aggregate;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@RedisHash(value = "refresh_token", timeToLive = 1000 * 60 * 60 * 24 * 7)   // 유효 7일
public class RefreshTokenRedis {
    @Id
    private String userId;
    @Indexed
    private String accessToken;
    @Indexed
    private String refreshToken;
    // 토큰 업데이트
    public void updateAccessToken(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}