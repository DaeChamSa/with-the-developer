package com.developer.command.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "email")
@Getter
public class Email {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_code")
    private Long emailCode;

    @Column(name = "email")
    private String email;

    @Column(name = "code")
    private String code;

    public Email(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public Email() {

    }
}
