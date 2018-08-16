package com.woowahan.smell.bazzangee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BazzangeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BazzangeeApplication.class, args);
    }
}
