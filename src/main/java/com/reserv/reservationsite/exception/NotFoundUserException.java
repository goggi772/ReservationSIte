package com.reserv.reservationsite.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundUserException extends RuntimeException{
    private final ErrorCode errorCode;

}
