package com.reserv.reservationsite.exception;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtRelationException extends RuntimeException {
    private final ErrorCode errorCode;
}
