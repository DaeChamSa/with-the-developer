package com.developer.userservice.user.command.domain.aggregate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "banned_user")
@Getter
@NoArgsConstructor
public class BannedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bannedCode;

    @NotNull
    private LocalDateTime bannedDate;

    @NotNull
    private Long userCode;

    public BannedUser(Long userCode) {
        this.bannedDate = LocalDateTime.now();
        this.userCode = userCode;
    }
}
