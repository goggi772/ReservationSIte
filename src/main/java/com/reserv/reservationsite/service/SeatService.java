package com.reserv.reservationsite.service;

import com.reserv.reservationsite.DTO.SeatDTO;
import com.reserv.reservationsite.core.entity.Seat;
import com.reserv.reservationsite.core.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    @Transactional
    public void reservation(SeatDTO dto) {
        Seat seat = seatRepository.findById(dto.getId()).orElseThrow(() ->
                new RuntimeException("자리가 존재하지 않습니다."));
        if (seat.getStatus().getStatus() == 0) {   //자리가 AVAILABLE일 때
            seat.seat_reserv(dto.getMember());
            seatRepository.save(seat);
        } else throw new RuntimeException("예약된 자리입니다.");  //exception핸들러 만들어야함

    }

}
