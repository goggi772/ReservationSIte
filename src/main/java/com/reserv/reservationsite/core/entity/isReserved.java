package com.reserv.reservationsite.core.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum isReserved {

    NOT_RESERVED("예약 가능"),
    IS_RESERVED_9("9시 타임 예약"),
    IS_RESERVED_10("10시 타임 예약"),
    IS_RESERVED_1920("19시 20분 타임 예약"),
    IS_RESERVED_2020("20시 20분 타임 예약");

    private final String message;



}
