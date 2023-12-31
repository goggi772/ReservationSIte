package com.reserv.reservationsite.controller;

import com.reserv.reservationsite.DTO.*;
import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.entity.Role;
import com.reserv.reservationsite.exception.ErrorCode;
import com.reserv.reservationsite.exception.ErrorResponse;
import com.reserv.reservationsite.exception.NotFoundUserException;
import com.reserv.reservationsite.jwt.JwtTokenProvider;
import com.reserv.reservationsite.service.MemberDetails;
import com.reserv.reservationsite.service.MemberService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate<String, Object> redisTemplate;

    @ResponseBody
    @PostMapping("/login")
    public TokenInfo login(@RequestBody LoginDTO dto) {
        return memberService.login(dto.getUsername(), dto.getPassword());
    }

    @PostMapping("/logout")
    public String logout(@AuthenticationPrincipal UserDetails userDetails) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey("refresh:" + userDetails.getUsername()))) {
            redisTemplate.delete("refresh:" + userDetails.getUsername());
        }
        return "redirect:/api/logout";
    }

    @ResponseBody
    @PostMapping("/register/admin")
    public ResponseEntity<ErrorResponse> registerAdmin(@RequestBody RegisterDTO dto) {
        try {
            return memberService.registerAdmin(dto);
        } catch (NotFoundUserException e) {
            return ErrorResponse.toResponseEntity(e.getErrorCode());
        } catch (Exception e) {
            return ErrorResponse.toResponseEntity(ErrorCode.UNKNOWN_ERROR);
        }
    }

    @ResponseBody
    @PostMapping("/api/refreshToken")
    public TokenInfo refreshAccessToken(@RequestBody TokenInfo tokenInfo) {
        return memberService.regenerationAccessToken(tokenInfo);
    }

    @ResponseBody
    @PostMapping("/changePw")
    public ResponseEntity<ErrorResponse> changePw(@AuthenticationPrincipal UserDetails userDetails,
                                                  @RequestBody ChangePwDTO dto) {
        return memberService.change_password(userDetails.getUsername(), dto.getOldPassword(), dto.getNewPassword());
    }

    @ResponseBody
    @GetMapping("/get/user")
    public String get_username(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails.getUsername();
    }


    @ResponseBody
    @GetMapping("/auth/user")
    public void auth_user(HttpServletResponse response,
                          @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities != null && !authorities.isEmpty()) {
            GrantedAuthority firstAuthority = authorities.iterator().next();
            String role = firstAuthority.getAuthority();
            if ("ROLE_USER".equals(role)) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            // authorities가 비어있거나 null일 경우에 대한 처리
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }


    }

    @ResponseBody
    @GetMapping("/auth/admin")
    public void auth_admin(@AuthenticationPrincipal UserDetails userDetails, HttpServletResponse response) throws IOException {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities != null && !authorities.isEmpty()) {
            GrantedAuthority firstAuthority = authorities.iterator().next();
            String role = firstAuthority.getAuthority();
            if ("ROLE_ADMIN".equals(role)) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            // authorities가 비어있거나 null일 경우에 대한 처리
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
