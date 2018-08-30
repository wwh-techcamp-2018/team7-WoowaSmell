package com.woowahan.smell.bazzangee;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BazzangeeApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "classpath:aws.yml,"
            + "/home/ubuntu/Project/travis/db-application.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(BazzangeeApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }
}
