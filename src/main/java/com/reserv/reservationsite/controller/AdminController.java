package com.reserv.reservationsite.controller;

import com.reserv.reservationsite.DTO.RegisterDTO;
import com.reserv.reservationsite.core.entity.Role;
import com.reserv.reservationsite.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @PostMapping("/register")
    public void register(HttpServletResponse response, @RequestBody RegisterDTO dto) throws Exception {

        try {
            memberService.register(dto);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    @PostMapping("/modifying")
    public void member_modifying(@RequestParam(value = "name") String name, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        try {
            String username = userDetails.getUsername();
            memberService.modifying(name, username);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/resetPassword")
    public void reset_password(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        try {
            String username = userDetails.getUsername();
            memberService.reset_password(username);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/auth/user")
    public void auth_user(@AuthenticationPrincipal UserDetails userDetails, HttpServletResponse response) throws IOException {
        GrantedAuthority authority = (GrantedAuthority) userDetails.getAuthorities();
        String role = authority.getAuthority();
        if (role.equals("ROLE_USER")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @PostMapping("/auth/admin")
    public void auth_admin(@AuthenticationPrincipal UserDetails userDetails, HttpServletResponse response) throws IOException {
        GrantedAuthority authority = (GrantedAuthority) userDetails.getAuthorities();
        String role = authority.getAuthority();
        if (role.equals("ROLE_ADMIN")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
