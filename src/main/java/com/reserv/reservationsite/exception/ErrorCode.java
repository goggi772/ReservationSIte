package com.reserv.reservationsite.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //400 BAD_REQUEST
    INVALID_ID(BAD_REQUEST, "유효하지 않은 ID입니다."),
    INVALID_INPUT_VALUE(BAD_REQUEST, "입력 양식과 맞지않는 입력값입니다."),
    NOT_EQUAL_PASSWORD(BAD_REQUEST, "비밀번호가 맞지 않습니다."),
    ALREADY_EXIST_USERNAME(BAD_REQUEST, "이미 존재하는 아이디입니다."),
    ALREADY_RESERVED(BAD_REQUEST, "이미 예약된 자리입니다."),
    NOT_EXIST_BIKE(BAD_REQUEST, "존재하지 않는 자리입니다."),
    NOT_EXIST_USER(BAD_REQUEST, "존재하지 않는 유저입니다."),
    RESERVATION_NOT_POSSIBLE(BAD_REQUEST, "예약 가능한 시간이 아닙니다."),

    // 401 UNAUTHORIZED
    INVALID_TOKEN(UNAUTHORIZED, "유효하지 않은 토큰입니다."),


    //404 NOT_FOUND
    NOT_FOUND_PROJECT(NOT_FOUND, "프로젝트를 찾을 수 없습니다."),
    NOT_FOUND_IMAGE(NOT_FOUND, "이미지를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}