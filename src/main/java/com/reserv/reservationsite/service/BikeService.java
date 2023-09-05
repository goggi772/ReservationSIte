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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BikeService {

    private final BikeRepository seatRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<ErrorResponse> reservation(BikeDTO dto, String username) {
        Bike bike = seatRepository.findById(dto.getBikeId()).orElseThrow(() ->
                new ReservationNotAvailableException(ErrorCode.NOT_EXIST_BIKE));
        Member member = memberRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("존재하지 않는 유저입니다."));
        if (bike.getStatus().equals(BikeStatus.available)) {   //자리가 AVAILABLE일 때
            bike.seat_reserv(member.getUsername());
            seatRepository.save(bike);
            return ErrorResponse.toResponseEntity(ErrorCode.RESERVATION_SUCCESSFUL);
        } else if (bike.getStatus().equals(BikeStatus.completed)){
            if (bike.getOwner().equals(member.getUsername())) {
                bike.cancel_reserv();
                seatRepository.save(bike);
                return ErrorResponse.toResponseEntity(ErrorCode.CANCEL_SUCCESSFUL);
            }
            else throw new ReservationNotAvailableException(ErrorCode.ALREADY_RESERVED);  // 이미 예약된 자리
        }
        else throw new ReservationNotAvailableException(ErrorCode.DISABLED_BIKE);  //이용 불가능한 자리
    }


    public List<Bike> findAllBikes() {
        return seatRepository.findAllByOrderByIdAsc();
    }

}
