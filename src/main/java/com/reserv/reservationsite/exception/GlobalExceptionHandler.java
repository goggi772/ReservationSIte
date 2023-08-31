package com.reserv.reservationsite.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleReservationNotAvailableException(ReservationNotAvailableException exception) {
        return ErrorResponse.toResponseEntity(exception.getErrorCode());
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundUserException(NotFoundUserException exception) {
        return ErrorResponse.toResponseEntity(exception.getErrorCode());
    }
}
