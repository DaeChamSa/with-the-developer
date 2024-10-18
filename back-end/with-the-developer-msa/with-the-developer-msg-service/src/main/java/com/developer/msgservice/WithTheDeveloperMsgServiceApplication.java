package com.developer.msgservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients
public class WithTheDeveloperMsgServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WithTheDeveloperMsgServiceApplication.class, args);
    }

}
