package com.developer.user.command.domain.aggregate;

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
    @ManyToOne
    @JoinColumn(name = "userCode")
    private User user;

    public BannedUser(User user) {
        this.bannedDate = LocalDateTime.now();
        this.user = user;
    }

    public BannedUser createBannedUser(User user) {
        return new BannedUser(user);
    }
}
