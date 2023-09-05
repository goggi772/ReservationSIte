package com.reserv.reservationsite.controller;

import com.reserv.reservationsite.DTO.LoginDTO;
import com.reserv.reservationsite.DTO.MemberDTO;
import com.reserv.reservationsite.DTO.TokenInfo;
import com.reserv.reservationsite.core.entity.Role;
import com.reserv.reservationsite.service.MemberDetails;
import com.reserv.reservationsite.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody LoginDTO dto) {
        return memberService.login(dto.getUsername(), dto.getPassword());
    }

    @GetMapping("/get/user")
    public MemberDTO get_username(@AuthenticationPrincipal UserDetails userDetails) {
        return memberService.findByMember(userDetails.getUsername());
    }


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

    @GetMapping("/auth/admin")
    public void auth_admin(@AuthenticationPrincipal UserDetails userDetails, HttpServletResponse response) throws IOException {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities != null && !authorities.isEmpty()) {
            GrantedAuthority firstAuthority = authorities.iterator().next();
            String role = firstAuthority.getAuthority();
            System.out.println("admin권한이 있나요");
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
