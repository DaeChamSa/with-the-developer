package com.developer.block.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "block")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockCode;

    @Column(name = "user_code", nullable = false)
    private Long userCode;

    @Builder
    public Block(Long userCode) {
        this.userCode = userCode;
    }
}
