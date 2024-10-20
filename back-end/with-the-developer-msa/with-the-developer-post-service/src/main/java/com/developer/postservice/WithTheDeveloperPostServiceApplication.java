package com.developer.postservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients
public class WithTheDeveloperPostServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WithTheDeveloperPostServiceApplication.class, args);
    }

}
