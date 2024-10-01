package com.developer.user.command.application.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.common.jwt.TokenDTO;
import com.developer.common.jwt.TokenProvider;
import com.developer.user.command.application.dto.LoginUserDTO;
import com.developer.user.command.application.dto.RegisterUserDTO;
import com.developer.user.command.application.dto.UpdateUserDTO;
import com.developer.user.command.domain.aggregate.BlackList;
import com.developer.user.command.domain.aggregate.RefreshToken;
import com.developer.user.command.domain.aggregate.User;
import com.developer.user.command.domain.aggregate.UserStatus;
import com.developer.user.command.domain.repository.BlackListRepository;
import com.developer.user.command.domain.repository.RefreshTokenRepository;
import com.developer.user.command.domain.repository.UserRepository;
import com.developer.user.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BlackListRepository blackListRepository;

    // 회원가입
    @Transactional
    public void registerUser(RegisterUserDTO userDTO) throws ParseException {

        // 중복 검증
        if (checkUserId(userDTO.getUserId())
                && checkUserEmail(userDTO.getUserEmail())
                && checkUserPhone(userDTO.getUserPhone())
                && checkUserNick(userDTO.getUserNick())) {

            // 비밀번호 암호화
            String encode = passwordEncoder.encode(userDTO.getUserPw());

            // userDTO에 비밀번호 넣기
            userDTO.setUserPw(encode);
            Date userDate = convertStringToDate(userDTO.getUserBirth());
            User user = new User(userDTO, userDate);

            log.info("User 객체 생성 {}", user);

            // DB에 저장
            userRepository.save(user);
        }
    }

    // 로그인
    @Transactional
    public TokenDTO loginUser(LoginUserDTO userDTO){

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = userDTO.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication);

        // 3. 사용자 역할 정보를 가져옴
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String userRole = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 5. tokenDTO에 권한 저장
        tokenDto.setUserRole(userRole);

        // 6. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(authentication.getName())
                .token(tokenDto.getRefreshToken())
                .build();

        log.info("RefreshToken 생성 {}", refreshToken);

        refreshTokenRepository.save(refreshToken);

        log.info("TokenDTO {}", tokenDto);

        return tokenDto;
    }

    // 회원 로그아웃 (일단 AccessBlack테이블 생성, 프론트단 생기면 없애기)
    @Transactional
    public void logoutUser(String userId, String accessToken){

        BlackList blackList = new BlackList(accessToken);
        blackListRepository.save(blackList);
        RefreshToken refreshToken = refreshTokenRepository
                .findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REFRESH_TOKEN));

        // 이미 밴 되어 있는 리프레쉬 토큰이면
        if (refreshToken.isBlack()){
            log.info("이미 밴 되어 있는 리프레쉬 토큰");
            throw new CustomException(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }

        refreshToken.addBlack();
    }

    // 회원 정보 수정
    @Transactional
    public void updateUser(String userId, UpdateUserDTO updateUserDTO) throws ParseException {
        User byUserID = findByUserID(userId);

        updateUserDTO.setUserPw(passwordEncoder.encode(updateUserDTO.getUserPw()));

        byUserID.updateUser(updateUserDTO);

        userRepository.save(byUserID);
    }

    // 회원탈퇴 (상태 변경)
    @Transactional
    public void deleteUser(String userId){
        User byUserID = findByUserID(userId);

        byUserID.updateUserStatus(UserStatus.DELETE);

        log.info("탈퇴 성공 userStatus {}", byUserID.getUserStatus());

        userRepository.save(byUserID);
    }

    // 사용자 아이디로 User 객체 찾기
    @Transactional
    public User findByUserID(String userId){
        Optional<User> byUserId = userRepository.findByUserId(userId);

        if (byUserId.isEmpty()){
            log.info("아이디가 존재하지 않음 {}", userId);
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        return byUserId.get();
    }

    // 아이디 중복 검증
    @Transactional
    public boolean checkUserId(String userId){
        Optional<User> byUserId = userRepository.findByUserId(userId);

        if (byUserId.isPresent()){
            log.info("아이디 값 중복 {}", userId);
            throw new CustomException(ErrorCode.DUPLICATE_USERID);
        }

        return true;
    }

    // 이메일 중복 검증
    @Transactional
    public boolean checkUserEmail(String userEmail){
        Optional<User> byUserEmail = userRepository.findByUserEmail(userEmail);

        if (byUserEmail.isPresent()){
            log.info("이메일 값 중복 {}", userEmail);
            throw new CustomException(ErrorCode.DUPLICATE_USEREMAIL);
        }

        return true;
    }

    // 닉네임 중복 검증
    @Transactional
    public boolean checkUserNick(String userNick){
        Optional<User> byUserNick = userRepository.findByUserNick(userNick);

        if (byUserNick.isPresent()){
            log.info("닉네임 값 중복 {}", userNick);
            throw new CustomException(ErrorCode.DUPLICATE_USERNICK);
        }

        return true;
    }

    // 핸드폰 번호 중복 검증
    @Transactional
    public boolean checkUserPhone(String userPhone){
        Optional<User> byUserPhone = userRepository.findByUserPhone(userPhone);

        if (byUserPhone.isPresent()){
            log.info("핸드폰 번호 값 중복 {}", userPhone);
            throw new CustomException(ErrorCode.DUPLICATE_USERPHONE);
        }

        return true;
    }

    // RefreshToken 재발급 서비스
    @Transactional
    public String reissue(String refreshToken){
        if (!tokenProvider.validateRefreshToken(refreshToken)){
            // 유효하지 않은 토큰 받았을때
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        // 받은 토큰으로 RefreshToken값 찾기
        RefreshToken rt = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REFRESH_TOKEN));

        // 만료되었으면
        if (!rt.getExpiryDate().isAfter(LocalDateTime.now())){
            throw new CustomException(ErrorCode.TOKEN_EXPIRED);
        }

        // Refresh 테이블에 들어있는 userId 찾아서 user가져오기
        User user = userRepository.findByUserId(rt.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return tokenProvider.generateAccessToken(user.getUserId(), refreshToken);
    }

    // 알림 수신 여부 허용
    @Transactional
    public void notiAccept(Long userCode){
        User user = userRepository.findByUserCode(userCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (user.isResNoti()){
            // 이미 알림이 허용 되어 있으면
            throw new CustomException(ErrorCode.NOTI_ALREADY_ACCEPT);
        }

        user.acceptResNoti();

        userRepository.save(user);
    }

    // 알림 수신 여부 거절
    @Transactional
    public void notiReject(Long userCode){
        User user = userRepository.findByUserCode(userCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (!user.isResNoti()){
            // 이미 알림이 거부 되어 있으면
            throw new CustomException(ErrorCode.NOTI_ALREADY_REJECT);
        }

        user.rejectResNoti();

        userRepository.save(user);
    }

    // 날짜 변환 메서드
    public Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }



}
