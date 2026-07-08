package com.sparta.developschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DevelopScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevelopScheduleApplication.class, args);
    }

}
