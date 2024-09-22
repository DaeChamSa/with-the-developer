package com.developer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WithTheDeveloperApplication {

    public static void main(String[] args) {
        SpringApplication.run(WithTheDeveloperApplication.class, args);
    }

}
