package com.developer.user.command.entity;

import com.developer.user.command.dto.RegisterUserDTO;
import com.developer.user.command.dto.UpdateUserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.text.ParseException;
import java.util.Date;

@NoArgsConstructor
@Entity
@Table(name = "user")
@Getter
@ToString
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private Long userCode;

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
    private int userWarning = 0;        // 신고 당한 횟수

    @Column(name = "user_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;      // 사용자 상태 Enum (ACTIVE, BAN, DELETE)

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    // DTO -> Entity 생성자
    public User(RegisterUserDTO userDTO, Date userBirth) {
        this.userId = userDTO.getUserId();
        this.userPw = userDTO.getUserPw();
        this.userEmail = userDTO.getUserEmail();
        this.userName = userDTO.getUserName();
        this.userNick = userDTO.getUserNick();
        this.userBirth = userBirth;
        this.userPhone = userDTO.getUserPhone();
        this.userStatus = UserStatus.ACTIVE;
        this.role = Role.USER;
    }

    // 사용자 정보 수정 메서드
    public void updateUser(UpdateUserDTO updateUserDTO) throws ParseException {
        if (updateUserDTO.getUserPw() != null) {
            this.userPw = updateUserDTO.getUserPw();
        }
        if (updateUserDTO.getUserEmail() != null) {
            this.userEmail = updateUserDTO.getUserEmail();
        }
        if (updateUserDTO.getUserName() != null) {
            this.userName = updateUserDTO.getUserName();
        }
        if (updateUserDTO.getUserNick() != null) {
            this.userNick = updateUserDTO.getUserNick();
        }
        if (updateUserDTO.getUserBirth() != null) {
            this.userBirth = updateUserDTO.convertStringToDate(updateUserDTO.getUserBirth());
        }
        if (updateUserDTO.getUserPhone() != null) {
            this.userPhone = updateUserDTO.getUserPhone();
        }
    }

    // 회원탈퇴 메서드 (userStatus 상태변경)
    public void deleteUser(){
        this.userStatus = UserStatus.DELETE;
    }
}
