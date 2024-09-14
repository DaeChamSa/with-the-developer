package com.developer.command.service;

import com.developer.command.dto.LoginUserDTO;
import com.developer.command.dto.RegisterUserDTO;
import com.developer.command.entity.User;
import com.developer.command.repository.UserRepository;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public void registerUser(RegisterUserDTO userDTO) throws ParseException {

        if (checkUserId(userDTO.getUserId()).isEmpty()){

            // 비밀번호 암호화
            String encode = passwordEncoder.encode(userDTO.getUserPw());

            // userDTO에 비밀번호 넣기
            userDTO.setUserPw(encode);
            Date userDate = convertStringToDate(userDTO.getUserBirth());
            User user = new User(userDTO, userDate);

            // User 객체로 변환 및 생성
//            User map = modelMapper.map(userDTO, User.class);

            log.info("User 객체 생성 {}", user);

            // DB에 저장
            userRepository.save(user);
        }
    }

    // 로그인
    @Transactional
    public void loginUser(LoginUserDTO userDTO){

        Optional<User> byUser = checkUserId(userDTO.getUserId());

        if (byUser.isPresent()){
            User user = byUser.get();

            if(!passwordEncoder.matches(user.getUserPw(), userDTO.getUserPw())){
                throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
            }
        }
    }


    // 아이디 검증 (중복 또는 로그인을 위해 Optional<User> 반환)
    @Transactional
    public Optional<User> checkUserId(String userId){
        Optional<User> byUserId = userRepository.findByUserId(userId);

        if (byUserId.isPresent()){
            log.info("아이디 값 중복 {}", userId);
            throw new CustomException(ErrorCode.DUPLICATE_USERID);
        }

        return byUserId;
    }

    public static Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }
}
