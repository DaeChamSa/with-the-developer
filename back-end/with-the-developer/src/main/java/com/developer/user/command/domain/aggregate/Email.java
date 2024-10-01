package com.developer.user.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "email")
@Getter
public class Email {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_code")
    private Long emailCode;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "code")
    private String code;

    @CreationTimestamp
    private Date sendDate;

    public Email(String userId, String email, String code) {
        this.userId = userId;
        this.userEmail = email;
        this.code = code;
    }

    public Email() {

    }
}
