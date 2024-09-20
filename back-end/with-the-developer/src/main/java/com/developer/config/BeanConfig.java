package com.developer.config;

import com.developer.user.query.dto.ResponseUserDTO;
import com.developer.user.command.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;

@Configuration
public class BeanConfig {

    // modelMapper 빈 등록
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        // 사용자 생일 변환 (Date -> String) 추가
        modelMapper.addMappings(new PropertyMap<User, ResponseUserDTO>() {
            @Override
            protected void configure() {
                using(ctx -> {
                    if (ctx.getSource() != null) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        return dateFormat.format(ctx.getSource());
                    }
                    return null;
                }).map(source.getUserBirth(), destination.getUserBirth());
            }
        });

        return modelMapper;
    }

    // 복호화가 불가능한 PasswordEncoder 빈 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

}
