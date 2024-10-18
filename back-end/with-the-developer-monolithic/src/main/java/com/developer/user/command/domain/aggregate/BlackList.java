package com.developer.user.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "black_list")
@NoArgsConstructor
public class BlackList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "black_list_code")
    private Long blackListCode;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    public BlackList(String accessToken) {
        this.accessToken = accessToken;
    }
}
