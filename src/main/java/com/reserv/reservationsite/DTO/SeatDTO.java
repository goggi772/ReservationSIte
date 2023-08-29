package com.reserv.reservationsite.DTO;

import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.entity.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SeatDTO {

    private Long id;

    private SeatStatus status;

    private Member member;

}
