package com.reserv.reservationsite.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Getter
@RedisHash("refresh")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {

    @Id
    private String username;
    private String refreshToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long timeToLive;
}
