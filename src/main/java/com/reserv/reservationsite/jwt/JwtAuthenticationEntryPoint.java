package com.reserv.reservationsite.jwt;


import com.reserv.reservationsite.exception.ErrorCode;
import com.reserv.reservationsite.exception.ErrorResponse;
import com.reserv.reservationsite.exception.JwtTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
//        String exception = (String)request.getAttribute("exception");
//        ErrorCode errorCode;
//
//        log.debug("log: exception: {} ", exception);
//
//        /**
//         * 토큰 없는 경우
//         **/
//        if(exception == null) {
//            errorCode = ErrorCode.LOGIN_FAILED;
//            setResponse(response, errorCode);
//            return;
//        }
//
//        /**
//         * 토큰 만료된 경우
//         **/
//        if(exception.equals(ErrorCode.EXPIRED_TOKEN.getCode())) {
//            errorCode = ErrorCode.EXPIRED_TOKEN;
//            setResponse(response, errorCode);
//            return;
//        }
//
//
//    /**
//     * 토큰 시그니처가 다른 경우
//     **/
//        if(exception.equals(ErrorCode.INVALID_JWT_TOKEN.getCode())) {
//            errorCode = ErrorCode.INVALID_JWT_TOKEN;
//            setResponse(response, errorCode);
//        }
//    }



    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        String error;
        if (e instanceof BadCredentialsException) {
            error = "Incorrect_ID_or_Password";
        } else if (e instanceof InternalAuthenticationServiceException) {
            error = "Internal_Authentication_Error";
        } else if (e instanceof AuthenticationCredentialsNotFoundException) {
            error = "Refuse_Authentication_Request";
        } else {
            error = "Unknown_Error";
        }
        System.out.println("e 메시지: "+e);
        String noAuthMessage = "{\n" +
                "    \"status\": \"FAILURE\",\n" +
                "    \"message\": \"" + error + "\"\n" +
                "}";
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON.toString());
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println(noAuthMessage);
    }
}
