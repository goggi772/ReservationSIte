package com.reserv.reservationsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.reserv.reservationsite.core.repository",
                            excludeFilters = @ComponentScan.Filter(
                                    type = FilterType.ASPECTJ, pattern = "com.reserv.reservationsite.core.redisRepository.*"
                            ))
@EnableRedisRepositories(basePackages = "com.reserv.reservationsite.core.redisRepository")
@SpringBootApplication
public class ReservationSIteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationSIteApplication.class, args);
    }

}
