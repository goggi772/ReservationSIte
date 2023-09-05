package com.reserv.reservationsite.service;

import com.reserv.reservationsite.DTO.BikeDTO;
import com.reserv.reservationsite.core.entity.Bike;
import com.reserv.reservationsite.core.entity.BikeStatus;
import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.repository.MemberRepository;
import com.reserv.reservationsite.core.repository.BikeRepository;
import com.reserv.reservationsite.exception.ErrorCode;
import com.reserv.reservationsite.exception.ErrorResponse;
import com.reserv.reservationsite.exception.ReservationNotAvailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BikeService {

    private final BikeRepository bikeRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<ErrorResponse> reservation(BikeDTO dto, String username) {
        Bike bike = bikeRepository.findById(dto.getBikeId()).orElseThrow(() ->
                new ReservationNotAvailableException(ErrorCode.NOT_EXIST_BIKE));
        Member member = memberRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("존재하지 않는 유저입니다."));
        if (bike.getStatus().equals(BikeStatus.available)) {   //자리가 AVAILABLE일 때
            bike.seat_reserv(member.getUsername());
            bikeRepository.save(bike);
            return ErrorResponse.toResponseEntity(ErrorCode.RESERVATION_SUCCESSFUL);
        } else if (bike.getStatus().equals(BikeStatus.completed)){
            if (bike.getOwner().equals(member.getUsername())) {
                bike.cancel_reserv();
                bikeRepository.save(bike);
                return ErrorResponse.toResponseEntity(ErrorCode.CANCEL_SUCCESSFUL);
            }
            else throw new ReservationNotAvailableException(ErrorCode.ALREADY_RESERVED);  // 이미 예약된 자리
        }
        else throw new ReservationNotAvailableException(ErrorCode.DISABLED_BIKE);  //이용 불가능한 자리
    }

    @Scheduled(cron = "0 5 8,9,10 ? * MON-FRI", zone = "Asia/Seoul")
    public void reset_bike() {  //월~금 8시5분, 9시5분, 10시 5분에 bike entity 초기화
        bikeRepository.resetBike();
        log.info("Bike Entity 초기화 완료");
    }

    @Scheduled(cron = "0 25 18,19,20,21 ? * MON-FRI", zone = "Asia/Seoul")
    public void reset_bike2() {   //월~금 6시25분, 7시25분, 8시25분, 9시25분에 bike entity 초기화
        bikeRepository.resetBike();
        log.info("Bike Entity 초기화 완료");
    }


    public List<Bike> findAllBikes() {
        return bikeRepository.findAllByOrderByIdAsc();
    }

}
