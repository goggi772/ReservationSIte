package com.reserv.reservationsite.DTO;

import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.entity.BikeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BikeDTO {

    private Long id;

    private BikeStatus status;

    private Member member;

}
