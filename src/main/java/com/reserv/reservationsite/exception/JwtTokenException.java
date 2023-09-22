package com.reserv.reservationsite.exception;

import io.jsonwebtoken.JwtException;
import lombok.Getter;

@Getter
public class JwtTokenException extends JwtException {
    private final ErrorCode errorCode;

    public JwtTokenException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
