package com.developer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class WithTheDeveloperApplication {

    public static void main(String[] args) {
        SpringApplication.run(WithTheDeveloperApplication.class, args);
    }

}
