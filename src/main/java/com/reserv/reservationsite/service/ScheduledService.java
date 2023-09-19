package com.reserv.reservationsite.service;

import com.reserv.reservationsite.core.repository.BikeRepository;
import com.reserv.reservationsite.core.repository.MemberRepository;
import com.reserv.reservationsite.core.entity.isReserved;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledService {

    private final MemberRepository memberRepository;

    private final BikeRepository bikeRepository;

    @Scheduled(cron = "0 25 18,21 ? * MON-FRI", zone = "Asia/Seoul")
    @Scheduled(cron = "0 5 8 ? * MON-FRI", zone = "Asia/Seoul")
    public void reset_bike() {  //월~금 8시5분, 9시5분, 10시 5분, 6시25분, 7시25분, 8시25분, 9시25분에 bike entity 초기화
        int num2 = bikeRepository.resetBike();  //bike 상태와 owner를 reset해주는 scheduler
        log.info("Bike Entity 초기화 완료: "+ num2);
    }

    @Scheduled(cron = "0 5 9 ? * MON-FRI", zone = "Asia/Seoul")
    public void update_isReserved_9() {
        int num = memberRepository.updateAllByIsReserved(isReserved.IS_RESERVED_9);//bike의 owner들의 isReserved변수를 true로 만들어주는 scheduler
        log.info("사용자 예약 상태 저장 완료: " + num + "명");
        int num2 = bikeRepository.resetBike();  //bike 상태와 owner를 reset해주는 scheduler
        log.info("Bike Entity 초기화 완료: "+ num2);
    }
    @Scheduled(cron = "0 5 10 ? * MON-FRI", zone = "Asia/Seoul")
    public void update_isReserved_10() {
        int num = memberRepository.updateAllByIsReserved(isReserved.IS_RESERVED_10);//bike의 owner들의 isReserved변수를 true로 만들어주는 scheduler
        log.info("사용자 예약 상태 저장 완료: " + num + "명");
        int num2 = bikeRepository.resetBike();  //bike 상태와 owner를 reset해주는 scheduler
        log.info("Bike Entity 초기화 완료: "+ num2);
    }
    @Scheduled(cron = "0 25 19 ? * MON-FRI", zone = "Asia/Seoul")
    public void update_isReserved_1920() {
        int num = memberRepository.updateAllByIsReserved(isReserved.IS_RESERVED_1920);//bike의 owner들의 isReserved변수를 true로 만들어주는 scheduler
        log.info("사용자 예약 상태 저장 완료: " + num + "명");
        int num2 = bikeRepository.resetBike();  //bike 상태와 owner를 reset해주는 scheduler
        log.info("Bike Entity 초기화 완료: "+ num2);
    }
    @Scheduled(cron = "0 25 20 ? * MON-FRI", zone = "Asia/Seoul")
    public void update_isReserved_2020() {
        int num = memberRepository.updateAllByIsReserved(isReserved.IS_RESERVED_2020);//bike의 owner들의 isReserved변수를 true로 만들어주는 scheduler
        log.info("사용자 예약 상태 저장 완료: " + num + "명");
        int num2 = bikeRepository.resetBike();  //bike 상태와 owner를 reset해주는 scheduler
        log.info("Bike Entity 초기화 완료: "+ num2);
    }

    @Scheduled(cron = "0 30 21 ? * MON-FRI", zone = "Asia/Seoul")
    public void reset_isReserved() {
        int num = memberRepository.resetIsReserved();
        log.info("사용자 예약 정보 isReserved 초기화 완료: "+num+"명");
    }

}
