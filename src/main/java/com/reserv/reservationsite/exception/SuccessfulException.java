package com.reserv.reservationsite.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SuccessfulException extends RuntimeException {
    private final ErrorCode errorCode;
}
