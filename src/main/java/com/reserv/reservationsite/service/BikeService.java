package com.reserv.reservationsite.service;

import com.reserv.reservationsite.DTO.BikeDTO;
import com.reserv.reservationsite.core.entity.Bike;
import com.reserv.reservationsite.core.entity.BikeStatus;
import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.repository.MemberRepository;
import com.reserv.reservationsite.core.repository.SeatRepository;
import com.reserv.reservationsite.exception.ErrorCode;
import com.reserv.reservationsite.exception.ReservationNotAvailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BikeService {

    private final SeatRepository seatRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public BikeDTO reservation(BikeDTO dto, String username) {
        Bike bike = seatRepository.findById(dto.getId()).orElseThrow(() ->
                new ReservationNotAvailableException(ErrorCode.NOT_EXIST_BIKE));
        Member member = memberRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("존재하지 않는 유저입니다."));
        if (bike.getStatus().equals(BikeStatus.available)) {   //자리가 AVAILABLE일 때
            bike.seat_reserv(dto.getMember());
            seatRepository.save(bike);
            return dto;
        } else throw new ReservationNotAvailableException(ErrorCode.ALREADY_RESERVED);  //exception핸들러 만들어야함

    }

    public List<Bike> findAll() {
        return seatRepository.findAll();
    }

}
