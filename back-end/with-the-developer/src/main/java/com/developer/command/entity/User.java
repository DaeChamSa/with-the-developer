package com.developer.command.entity;

import com.developer.command.dto.RegisterUserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Table(name = "user")
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private long userCode;

    @Column(name = "user_id", unique = true, nullable = false)
    private String userId;          // 아이디

    @Column(name = "user_pw", nullable = false)
    private String userPw;          // 비밀번호

    @Column(name = "user_email", unique = true, nullable = false)
    private String userEmail;       // 사용자 이메일

    @Column(name = "user_name", nullable = false)
    private String userName;        // 사용자 이름

    @Column(name = "user_nick", unique = true, nullable = false)
    private String userNick;        // 닉네임

    @Column(name = "user_birth", nullable = false)
    private Date userBirth;         // 사용자 생일

    @Column(name = "user_phone", unique = true, nullable = false)
    private String userPhone;       // 사용자 폰번호

    @Column(name = "user_warning")
    @ColumnDefault("0")
    private int userWarning;        // 신고 당한 횟수

    @Column(name = "user_status", nullable = false)
    private String userStatus;      // 사용자 상태

    public User(RegisterUserDTO userDTO, Date userBirth) {
        this.userId = userDTO.getUserId();
        this.userPw = userDTO.getUserPw();
        this.userEmail = userDTO.getUserEmail();
        this.userName = userDTO.getUserName();
        this.userNick = userDTO.getUserNick();
        this.userBirth = userBirth;
        this.userPhone = userDTO.getUserPhone();
        this.userStatus = "true";
    }

    public User() {
    }
}
