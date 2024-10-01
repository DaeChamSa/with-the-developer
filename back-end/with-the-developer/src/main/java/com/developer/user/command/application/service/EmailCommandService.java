package com.developer.user.command.application.service;


import com.developer.user.command.application.dto.SendEmailDTO;
import com.developer.user.command.domain.aggregate.Email;
import com.developer.user.command.domain.repository.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailCommandService {

    private final JavaMailSender emailSender;
    private String authNum;

    private final EmailRepository emailRepository;


    /**
     * 인증번호 생성
     */
    public void createCode(){
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i<8; i++){
            // 0 ~ 2 사이 값을 랜덤으로 집어넣음
            int idx = random.nextInt(3);

            switch (idx){
                case 0:
                    // a(97) ~ z(122)
                    key.append((char) ((int)random.nextInt(26) + 97));
                    break;
                case 1:
                    // A(65) ~ Z(90)
                    key.append((char) ((int)random.nextInt(26) + 65));
                    break;
                case 2:
                    // 0 ~ 9
                    key.append(random.nextInt(9));
                    break;
            }
        }
        authNum = key.toString();
    }

    /**
     * 메일 양식 작성
     */
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        // 코드 생성
        createCode();
        String setFrom = "test@gmail.com";
        String toEmail = email;
        String title = "회원가입 인증코드";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(title);

        // 메일 내용
        String msgOfEmail="";
        msgOfEmail += "<div style='margin:20px;'>";
        msgOfEmail += "<h1> 안녕하세요! . </h1>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>아래 코드를 입력해주세요<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>감사합니다.<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgOfEmail += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgOfEmail += "<div style='font-size:130%'>";
        msgOfEmail += "CODE : <strong>";
        msgOfEmail += authNum + "</strong><div><br/> ";
        msgOfEmail += "</div>";

        message.setFrom(setFrom);
        message.setText(msgOfEmail, "utf-8", "html");

        return message;
    }

    /**
     * 실제 메일 전송
     */
    public String sendEmail(SendEmailDTO sendEmailDTO) throws MessagingException, UnsupportedEncodingException {
        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(sendEmailDTO.getUserEmail());
        //실제 메일 전송
        emailSender.send(emailForm);
        Email email1 = new Email(sendEmailDTO.getUserId(), sendEmailDTO.getUserEmail(), authNum);
        emailRepository.save(email1);

        return authNum; //인증 코드 반환
    }

    /**
     * 인증 코드 확인 절차
     */
//    public String codeCheck(String code) throws CustomException {
//        log.info("request code {}", code);
//        Optional<Email> byCode = emailRepository.findByCode(code);
//        log.info("byCode {}", byCode);
//        log.info("response code {}" ,byCode.get());
//        if (byCode.isPresent()){
//            return "OK";
//        } else {
//            throw new CustomException(ErrorCode.NOT_FOUNDED_CODE);
//        }
//    }
}
