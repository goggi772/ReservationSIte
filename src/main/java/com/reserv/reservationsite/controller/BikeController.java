package com.reserv.reservationsite.controller;

import com.reserv.reservationsite.DTO.BikeDTO;
import com.reserv.reservationsite.core.entity.Bike;
import com.reserv.reservationsite.exception.ErrorCode;
import com.reserv.reservationsite.exception.ErrorResponse;
import com.reserv.reservationsite.service.BikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/bike")
@RequiredArgsConstructor
public class BikeController {

    private final BikeService bikeService;

    @GetMapping("")
    public List<Bike> bike_information() {
        return bikeService.findAllBikes();
    }


    @PutMapping("/reservation")
    public ResponseEntity<ErrorResponse> reservation(@AuthenticationPrincipal UserDetails userDetails, @RequestBody BikeDTO dto, HttpServletResponse response) {
        try {
            String username = userDetails.getUsername();
            return bikeService.reservation(dto, username);
        } catch (Exception e) {
            return ErrorResponse.toResponseEntity(ErrorCode.RESERVE_ONLY_ONE_BIKE);
        }
    }
}
