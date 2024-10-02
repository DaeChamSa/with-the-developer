package com.developer.user.command.domain.aggregate;

import com.developer.user.command.application.dto.RegisterUserDTO;
import com.developer.user.command.application.dto.UpdateUserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDate userBirth;         // 사용자 생일

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

    @Column(name = "res_noti", nullable = false)
    @ColumnDefault("true")
    private boolean resNoti;    // 알림 수신 여부

    // DTO -> Entity 생성자
    public User(RegisterUserDTO userDTO, LocalDate userBirth) {
        this.userId = userDTO.getUserId();
        this.userPw = userDTO.getUserPw();
        this.userEmail = userDTO.getUserEmail();
        this.userName = userDTO.getUserName();
        this.userNick = userDTO.getUserNick();
        this.userBirth = userBirth;
        this.userPhone = userDTO.getUserPhone();
        this.userStatus = UserStatus.ACTIVE;
        this.role = Role.USER;
        this.resNoti = true;
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

    // 회원 상태 변경 메서드
    public void updateUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public int updateUserWarning() {
        this.userWarning++;
        return this.userWarning;
    }

    // 알림 수신 허용
    public void acceptResNoti(){
        this.resNoti = true;
    }

    // 알림 수신 거부
    public void rejectResNoti(){
        this.resNoti = false;
    }

    // ADMIN 계정 생성 생성자
    public User(String adminId, String adminPw, String adminName, String adminNick){
        this.userId = adminId;
        this.userPw = adminPw;
        this.userEmail = "admin@admin.com";
        this.userName = adminName;
        this.userNick = adminNick;
        this.userBirth = LocalDate.now().minusYears(10);
        this.userPhone = "000-0000-0000";
        this.userStatus = UserStatus.ACTIVE;
        this.role = Role.ADMIN;
    }

    // 비밀번호 재설정
    public void pwResetting(String resettingPw){

        this.userPw = resettingPw;
    }
}
