package com.reserv.reservationsite.exception;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class JwtRelationException extends JwtException {
    private final ErrorCode errorCode;

    public JwtRelationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
