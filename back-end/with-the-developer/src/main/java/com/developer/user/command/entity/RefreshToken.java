package com.developer.user.command.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter
@Table(name = "refresh_token")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RefreshToken {

    @Id
    @Column(name = "rt_id")
    private String userId;    // 사용자 ID = RT키 값

    @Column(name = "rt_token", unique = true, nullable = false)
    private String token;   // Refresh Token값

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;   // 만료날짜

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;  // 생성날짜

    @Builder
    public RefreshToken(String userId, String token){
        this.userId = userId;
        this.token = token;
        this.expiryDate = LocalDateTime.now().plusDays(7);
        this.createdDate = LocalDateTime.now();
    }


}
