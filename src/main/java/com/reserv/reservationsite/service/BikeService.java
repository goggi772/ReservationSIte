package com.reserv.reservationsite.service;

import com.reserv.reservationsite.DTO.BikeDTO;
import com.reserv.reservationsite.core.entity.Bike;
import com.reserv.reservationsite.core.entity.BikeStatus;
import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.entity.isReserved;
import com.reserv.reservationsite.core.repository.MemberRepository;
import com.reserv.reservationsite.core.repository.BikeRepository;
import com.reserv.reservationsite.exception.ErrorCode;
import com.reserv.reservationsite.exception.ErrorResponse;
import com.reserv.reservationsite.exception.ReservationNotAvailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            if (!member.getIsReserved().equals(isReserved.NOT_RESERVED) && !member.isVIP()) {  //하루에 한번 예약 가능
                    throw new ReservationNotAvailableException(ErrorCode.RESERVATION_ONLY_ONCE_A_DAY);
            } else {
                bike.seat_reserv(member.getUsername());
                bikeRepository.save(bike);
                member.set_reserved_time(ZonedDateTime.now(ZoneId.of("Asia/Seoul")));
                memberRepository.save(member);
                return ErrorResponse.toResponseEntity(ErrorCode.RESERVATION_SUCCESSFUL);
            }
        } else if (bike.getStatus().equals(BikeStatus.completed)){
            if (bike.getOwner().equals(member.getUsername())) {
                bike.change_bikeStatus(BikeStatus.available);
                bikeRepository.save(bike);
                member.set_reserved_time(null);
                memberRepository.save(member);
                return ErrorResponse.toResponseEntity(ErrorCode.CANCEL_SUCCESSFUL);
            }
            else throw new ReservationNotAvailableException(ErrorCode.ALREADY_RESERVED);  // 이미 예약된 자리
        }
        else throw new ReservationNotAvailableException(ErrorCode.DISABLED_BIKE);  //이용 불가능한 자리
    }

    public List<Bike> findAllBikes() {
        return bikeRepository.findAllByOrderByIdAsc();
    }

    @Cacheable(value = "bikeCache", key = "'Bikes_Clone'")
    public List<Bike> getAllBikesToCache() {
        log.info("캐시 생성");
        return bikeRepository.findAllByOrderByIdAsc();

    }
    @CacheEvict(value = "bikeCache", key = "'Bikes_Clone'")
    public void evictAllBikesCache() {
        log.info("캐시 비워짐");
    }

    @Transactional
    public ResponseEntity<ErrorResponse> admin_reservation_cancel(BikeDTO dto) {
        Bike bike = bikeRepository.findById(dto.getBikeId()).orElseThrow(() ->
                new ReservationNotAvailableException(ErrorCode.NOT_EXIST_BIKE));
        Member member = memberRepository.findByUsername(bike.getOwner()).orElseThrow(() ->
                new UsernameNotFoundException("존재하지 않는 유저입니다."));
        if (bike.getStatus().equals(BikeStatus.completed) && bike.getOwner() != null) {
            bike.change_bikeStatus(BikeStatus.available);
            bikeRepository.save(bike);
            member.set_reserved_time(null);
            memberRepository.save(member);
            return ErrorResponse.toResponseEntity(ErrorCode.CANCEL_SUCCESSFUL);
        } else throw new ReservationNotAvailableException(ErrorCode.ALREADY_CANCEL);
    }

    @Transactional
    public void bike_disabled(BikeDTO dto) {
        Bike bike = bikeRepository.findById(dto.getBikeId()).orElseThrow(() ->
                new ReservationNotAvailableException(ErrorCode.NOT_EXIST_BIKE));
        if (bike.getStatus().equals(BikeStatus.available)) {
            bike.change_bikeStatus(BikeStatus.disabled);
            bikeRepository.save(bike);
        } else if (bike.getStatus().equals(BikeStatus.disabled)) {
            bike.change_bikeStatus(BikeStatus.available);
            bikeRepository.save(bike);
        } else throw new ReservationNotAvailableException(ErrorCode.ALREADY_RESERVED);
    }

}
