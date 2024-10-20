package com.developer.cartservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class WithTheDeveloperCartGoodsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WithTheDeveloperCartGoodsServiceApplication.class, args);
    }

}
