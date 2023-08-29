package com.reserv.reservationsite.core.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeatStatus {

    AVAILABLE(0),
    UNAVAILABLE(1);

    private final int status;

}
