package com.reserv.reservationsite.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //200 OK
    STATUS_OK(OK, "성공"),
    RESERVATION_SUCCESSFUL(OK, "예약 되었습니다."),
    CANCEL_SUCCESSFUL(OK, "예약이 취소되었습니다."),


    //400 BAD_REQUEST
    INVALID_INPUT_VALUE(BAD_REQUEST, "입력 양식과 맞지않는 입력값입니다."),
    UNKNOWN_ERROR(BAD_REQUEST, "알 수 없는 오류입니다."),
    ALREADY_RESERVED(BAD_REQUEST, "이미 예약된 자리입니다."),
    RESERVE_ONLY_ONE_BIKE(BAD_REQUEST, "하나의 자리만 예약할 수 있습니다."),
    DISABLED_BIKE(BAD_REQUEST, "이용 불가능한 자리입니다."),
    RESERVATION_ONLY_ONCE_A_DAY(BAD_REQUEST, "하루에 한 타임만 이용하실 수 있습니다."),
    NOT_EXIST_BIKE(BAD_REQUEST, "존재하지 않는 자리입니다."),
    NOT_EXIST_USER(BAD_REQUEST, "존재하지 않는 유저입니다."),
    ALREADY_EXIST_USERNAME(BAD_REQUEST, "존재하는 이름입니다."),
    RESERVATION_NOT_POSSIBLE(BAD_REQUEST, "예약 가능한 시간이 아닙니다."),
    ALREADY_CANCEL(BAD_REQUEST, "이미 예약이 취소되었습니다."),
    INCORRECT_ID_PASSWORD(BAD_REQUEST, "아이디 혹은 비밀번호가 맞지 않습니다."),
    INVALID_TOKEN(BAD_REQUEST, "유효하지 않은 토큰입니다."),

    // 401 UNAUTHORIZED
    EXPIRED_TOKEN(UNAUTHORIZED, "만료된 토큰입니다."),
    TOKEN_ERROR(UNAUTHORIZED, "토큰이 만료되거나 유효하지 않습니다."),


    //404 NOT_FOUND
    NOT_FOUND_PROJECT(NOT_FOUND, "프로젝트를 찾을 수 없습니다."),
    NOT_FOUND_IMAGE(NOT_FOUND, "이미지를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}