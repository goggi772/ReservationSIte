package com.reserv.reservationsite.service;

import com.reserv.reservationsite.core.entity.Bike;
import com.reserv.reservationsite.core.repository.BikeRepository;
import com.reserv.reservationsite.core.repository.MemberRepository;
import com.reserv.reservationsite.core.entity.isReserved;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledService {

    private final MemberRepository memberRepository;

    private final BikeRepository bikeRepository;

    private final BikeService bikeService;

    private final RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0 13 18,19,20,21 ? * MON-SAT", zone = "Asia/Seoul")
    @Scheduled(cron = "0 0 8,9,10 ? * MON-FRI", zone = "Asia/Seoul")
    public void reset_bike() {  //월~금 8시5분, 9시5분, 10시 5분, 6시25분, 7시25분, 8시25분, 9시25분에 bike entity 초기화
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour == 9) {
            int num = memberRepository.updateAllByIsReserved(isReserved.IS_RESERVED_9);//bike의 owner들의 isReserved변수를 true로 만들어주는 scheduler
            log.info("사용자 예약 상태 저장 완료: " + num + "명");
        } else if (hour == 10) {
            int num = memberRepository.updateAllByIsReserved(isReserved.IS_RESERVED_10);//bike의 owner들의 isReserved변수를 true로 만들어주는 scheduler
            log.info("사용자 예약 상태 저장 완료: " + num + "명");
        } else if (hour == 18) {
            int num = memberRepository.updateAllByIsReserved(isReserved.IS_RESERVED_1920);//bike의 owner들의 isReserved변수를 true로 만들어주는 scheduler
            log.info("사용자 예약 상태 저장 완료: " + num + "명");
        } else if (hour == 20) {
            int num = memberRepository.updateAllByIsReserved(isReserved.IS_RESERVED_2020);//bike의 owner들의 isReserved변수를 true로 만들어주는 scheduler
            log.info("사용자 예약 상태 저장 완료: " + num + "명");
        }

        bikeService.evictAllBikesCache();
        bikeService.getAllBikesToCache();

//        redisTemplate.opsForList().leftPushAll("Bike_Clone", bikes, Duration.ofHours(1));
//        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//        valueOperations.set("Bike_Clone", bikes, Duration.ofHours(1));  //전 타임 bike예약 정보 저장
        log.info("Bike 상태 저장 완료");

        int num2 = bikeRepository.resetBike();  //bike 상태와 owner를 reset해주는 scheduler
        log.info("Bike Entity 초기화 완료: "+ num2);
    }


    @Scheduled(cron = "0 30 21 ? * MON-FRI", zone = "Asia/Seoul")
    public void reset_isReserved() {
        int num = memberRepository.resetIsReserved();
        log.info("사용자 예약 정보 isReserved 초기화 완료: "+num+"명");
    }

}
