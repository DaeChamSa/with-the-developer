package com.developer.block.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blocked_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BlockedUser {

    @Id
    @Column(name = "block_code")
    private Long blockCode;

    @Column(name = "user_code", nullable = false)
    private Long userCode;

    @Builder
    public BlockedUser(Long blockCode, Long userCode) {
        this.blockCode = blockCode;
        this.userCode = userCode;
    }
}
