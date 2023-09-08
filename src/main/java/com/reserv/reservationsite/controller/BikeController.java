package com.reserv.reservationsite.controller;

import com.reserv.reservationsite.DTO.BikeDTO;
import com.reserv.reservationsite.core.entity.Bike;
import com.reserv.reservationsite.exception.ErrorCode;
import com.reserv.reservationsite.exception.ErrorResponse;
import com.reserv.reservationsite.exception.ReservationNotAvailableException;
import com.reserv.reservationsite.service.BikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
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


    @Cacheable(value = "isReserved")
    @PutMapping("/reservation")
    public ResponseEntity<ErrorResponse> reservation(@AuthenticationPrincipal UserDetails userDetails, @RequestBody BikeDTO dto, HttpServletResponse response) {
        try {
            String username = userDetails.getUsername();
            return bikeService.reservation(dto, username);
        } catch (ReservationNotAvailableException e) {
            System.out.println(e.getErrorCode());
            return ErrorResponse.toResponseEntity(e.getErrorCode());
        } catch (Exception e) {
            return ErrorResponse.toResponseEntity(ErrorCode.RESERVE_ONLY_ONE_BIKE);  //하나의 자리만 예약가능(db unique로)
        }
    }

    @PostMapping("/test")
    public int test() {
        return bikeService.test();
    }
}
