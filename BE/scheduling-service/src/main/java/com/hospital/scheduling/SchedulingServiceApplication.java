package com.hospital.scheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SchedulingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulingServiceApplication.class, args);
    }

}
