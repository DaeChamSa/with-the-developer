package com.developer.config;

import com.developer.user.command.domain.aggregate.User;
import com.developer.user.query.dto.ResponseUserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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


}
