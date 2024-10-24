package com.developer.user.command.domain.aggregate;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@RedisHash(value = "black_list", timeToLive = 1000 * 60 * 30)   // 유효 30분
public class BlackListRedis {
    @Id
    private String accessToken;
    @Indexed
    private String userId;
}