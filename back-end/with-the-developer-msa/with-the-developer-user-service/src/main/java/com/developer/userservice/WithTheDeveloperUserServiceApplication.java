package com.developer.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableRedisRepositories(basePackages = "com.developer.userservice.user.command.domain.repository") // 리포지토리 패키지 경로
public class WithTheDeveloperUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WithTheDeveloperUserServiceApplication.class, args);
    }

}
