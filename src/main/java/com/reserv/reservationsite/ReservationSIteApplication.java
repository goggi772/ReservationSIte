package com.reserv.reservationsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication
public class ReservationSIteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationSIteApplication.class, args);
    }

}