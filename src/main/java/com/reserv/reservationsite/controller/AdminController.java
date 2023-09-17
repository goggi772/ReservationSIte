package com.reserv.reservationsite.controller;

import com.reserv.reservationsite.DTO.BikeDTO;
import com.reserv.reservationsite.DTO.MemberDTO;
import com.reserv.reservationsite.DTO.RegisterDTO;
import com.reserv.reservationsite.core.entity.Role;
import com.reserv.reservationsite.exception.ErrorCode;
import com.reserv.reservationsite.exception.ErrorResponse;
import com.reserv.reservationsite.exception.NotFoundUserException;
import com.reserv.reservationsite.service.BikeService;
import com.reserv.reservationsite.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BikeService bikeService;

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<ErrorResponse> register(@RequestBody RegisterDTO dto) throws Exception {

        try {
            return memberService.register(dto);
        } catch (NotFoundUserException e) {
            return ErrorResponse.toResponseEntity(e.getErrorCode());
        } catch (Exception e) {
            return ErrorResponse.toResponseEntity(ErrorCode.UNKNOWN_ERROR);
        }

    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ErrorResponse> reset_password(@RequestBody String username) throws Exception {
        try {
            System.out.println(username);
            return memberService.reset_password(username);

        } catch (NotFoundUserException e) {
            return ErrorResponse.toResponseEntity(e.getErrorCode());
        } catch (Exception e) {
            return ErrorResponse.toResponseEntity(ErrorCode.UNKNOWN_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ErrorResponse> delete_user(@RequestBody String username) {
        try {
            memberService.delete_user_info(username);
            return ErrorResponse.toResponseEntity(ErrorCode.STATUS_OK);
        } catch (Exception e) {
            return ErrorResponse.toResponseEntity(ErrorCode.NOT_EXIST_USER);
        }

    }

    @PutMapping("/reservation/cancel")
    public ResponseEntity<ErrorResponse> cancel_reservation(@RequestBody BikeDTO dto) {
        return bikeService.admin_reservation_cancel(dto);
    }

    @PutMapping("/reservation/disabled")
    public void bike_disabled(@RequestBody BikeDTO dto) {
        bikeService.bike_disabled(dto);
    }

    @GetMapping("/member/view")
    public Page<MemberDTO> get_page_member(@RequestParam Integer pageNo,
                                           @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return memberService.get_member_info(pageable);
    }
}
