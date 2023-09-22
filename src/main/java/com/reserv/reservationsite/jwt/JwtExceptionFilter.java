package com.reserv.reservationsite.jwt;

import com.reserv.reservationsite.exception.ErrorCode;
import com.reserv.reservationsite.exception.JwtTokenException;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtTokenException e) {
            ErrorCode ec = e.getErrorCode();
            response.setStatus(ec.getHttpStatus().value());
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\n" +
                    "    \"status\": \"" + ec.getHttpStatus().value() + "\",\n" +
                    "    \"message\": \"" + e.getMessage() + "\"\n" +
                    "}");
        }
    }

}
