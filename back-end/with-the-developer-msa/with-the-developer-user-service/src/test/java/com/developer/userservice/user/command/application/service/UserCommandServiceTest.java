package com.developer.userservice.user.command.application.service;

import com.developer.postservice.common.exception.CustomException;
import com.developer.postservice.common.exception.ErrorCode;
import com.developer.common.jwt.TokenDTO;
import com.developer.user.command.application.dto.*;
import com.developer.user.command.domain.aggregate.BlackList;
import com.developer.user.command.domain.aggregate.RefreshToken;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.aggregate.UserStatus;
import com.developer.user.command.domain.repository.BlackListRepository;
import com.developer.user.command.domain.repository.EmailRepository;
import com.developer.user.command.domain.repository.RefreshTokenRepository;
import com.developer.user.command.domain.repository.UserRepository;
import com.developer.user.query.mapper.BlackListMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserCommandServiceTest {

    @Autowired
    private UserCommandService userCommandService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private BlackListMapper blackListMapper;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private EmailCommandService emailCommandService;

    @BeforeEach
    public void userSetUp() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setUserId("test01Id");
        registerUserDTO.setUserPw("test01Pw");
        registerUserDTO.setUserEmail("test01@test.com");
        registerUserDTO.setUserName("test01Name");
        registerUserDTO.setUserNick("test01Nick");
        registerUserDTO.setUserBirth("1990-01-01");
        registerUserDTO.setUserPhone("010-0000-0000");

        userCommandService.registerUser(registerUserDTO);
    }

    @Test
    @DisplayName("회원가입을 할 수 있다.")
    void registerUser() {
        // Given
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setUserId("test02Id");
        registerUserDTO.setUserPw("test02Pw");
        registerUserDTO.setUserEmail("test02@test.com");
        registerUserDTO.setUserName("test02Name");
        registerUserDTO.setUserNick("test02Nick");
        registerUserDTO.setUserBirth("1990-01-01");
        registerUserDTO.setUserPhone("010-1111-1111");

        // When
        Long testCode = userCommandService.registerUser(registerUserDTO);
        User user = userRepository.findByUserCode(testCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // Then
        assertEquals("test02Id", user.getUserId());     // 아이디 검증
        assertTrue(passwordEncoder.matches("test02Pw", user.getUserPw()));      // 비밀번호 검증
        assertEquals("test02@test.com", user.getUserEmail());       // 이메일 검증
        assertEquals("test02Name", user.getUserName());         // 이름 검증
        assertEquals("test02Nick", user.getUserNick());             // 닉네임 검증
        assertEquals("1990-01-01", user.getUserBirth().toString());     // 생일 검증
        assertEquals("010-1111-1111", user.getUserPhone());         // 핸드폰 번호 검증
    }

    @Test
    @DisplayName("로그인을 할 수 있다.")
    void loginUser() {
        // Given
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setUserId("test01Id");
        loginUserDTO.setUserPw("test01Pw");

        // When
        TokenDTO tokenDTO = userCommandService.loginUser(loginUserDTO);

        // Then
        assertNotNull(tokenDTO);
        assertEquals("ROLE_USER", tokenDTO.getUserRole());
    }

    @Test
    @DisplayName("로그아웃을 할 수 있다. BlackList 등록")
    void logoutUser() {
        // Given
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setUserId("test01Id");
        loginUserDTO.setUserPw("test01Pw");

        TokenDTO tokenDTO = userCommandService.loginUser(loginUserDTO);

        // When
        userCommandService.logoutUser(loginUserDTO.getUserId(), tokenDTO.getAccessToken());

        // Then
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(loginUserDTO.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REFRESH_TOKEN));

        assertTrue(refreshToken.isBlack());
        assertNotNull(blackListMapper.findByAccessToken(tokenDTO.getAccessToken()));

    }

    @Test
    @DisplayName("사용자 정보를 수정 할 수 있다.")
    void updateUser() {
        // Given
        String userId = "test01Id";

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setUserPw("update01Pw");
        updateUserDTO.setUserEmail("update01@test.com");
        updateUserDTO.setUserName("update01Name");
        updateUserDTO.setUserNick("update01Nick");
        updateUserDTO.setUserBirth("2000-01-01");
        updateUserDTO.setUserPhone("010-1234-1234");

        // When
        userCommandService.updateUser(userId, updateUserDTO);

        // Then
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        assertTrue(passwordEncoder.matches("update01Pw", user.getUserPw()));
        assertEquals(updateUserDTO.getUserEmail(), user.getUserEmail());
        assertEquals(updateUserDTO.getUserName(), user.getUserName());
        assertEquals(updateUserDTO.getUserNick(), user.getUserNick());
        assertEquals(updateUserDTO.getUserBirth(), user.getUserBirth().toString());
        assertEquals(updateUserDTO.getUserPhone(), user.getUserPhone());
    }

    @Test
    @DisplayName("유저를 탈퇴처리 할 수 있다.")
    void deleteUser() {
        // Given
        String userId = "test01Id";

        // When
        userCommandService.deleteUser(userId);

        // Then
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        assertEquals(UserStatus.DELETE, user.getUserStatus());
    }

    @Test
    @DisplayName("유저아이디로 정보를 조회 할 수 있다.")
    void findByUserID() {
        // Given
        String userId = "test01Id";

        // When
        User byUserID = userCommandService.findByUserID(userId);

        // Then
        assertNotNull(byUserID);
        assertEquals("test01Id", byUserID.getUserId());
        assertEquals(UserStatus.ACTIVE, byUserID.getUserStatus());
        assertEquals("test01Name", byUserID.getUserName());
        assertEquals("test01Nick", byUserID.getUserNick());
        assertEquals("1990-01-01", byUserID.getUserBirth().toString());
        assertEquals("010-0000-0000", byUserID.getUserPhone());

    }

    @Test
    @DisplayName("AccessToken 재발급을 할 수 있다.")
    void reissue() {
        // Given
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setUserId("test01Id");
        loginUserDTO.setUserPw("test01Pw");

        TokenDTO tokenDTO = userCommandService.loginUser(loginUserDTO);

        // When
        String reissue = userCommandService.reissue(tokenDTO.getRefreshToken());

        // Then
        assertNotNull(reissue);
    }

    @Test
    @DisplayName("알림 수신을 변경 할 수 있다.")
    void notiChange() {
        // Given
        User user = userRepository.findByUserId("test01Id")
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        user.rejectResNoti();

        // When
        userCommandService.notiAcceptableChange(user.getUserCode());

        // Then
        User beforeUser = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        assertTrue(beforeUser.isResNoti());
    }

    @Test
    @DisplayName("비밀번호를 재설정 할 수 있다.")
    void pwResetting() throws Exception{
        // Given
        SendEmailDTO sendEmailDTO = new SendEmailDTO();
        sendEmailDTO.setUserId("test01Id");
        sendEmailDTO.setUserEmail("test01@test.com");

        String authNum = emailCommandService.sendEmail(sendEmailDTO);

        PwResettingDTO pwResettingDTO = new PwResettingDTO();
        pwResettingDTO.setUserId("test01Id");
        pwResettingDTO.setCode(authNum);
        pwResettingDTO.setUserPw("reset01Pw");

        // When
        userCommandService.pwResetting(pwResettingDTO);

        // Then
        User user = userRepository.findByUserId("test01Id")
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        assertTrue(passwordEncoder.matches("reset01Pw", user.getUserPw()));
    }
}