package com.reserv.reservationsite.controller;

import com.reserv.reservationsite.DTO.BikeDTO;
import com.reserv.reservationsite.core.entity.Bike;
import com.reserv.reservationsite.service.BikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/bike")
@RequiredArgsConstructor
public class BikeController {

    private final BikeService bikeService;

    @GetMapping("/")
    public List<Bike> bike_information() {
        return bikeService.findAll();
    }

    @PostMapping("/reservation")
    public BikeDTO reservation(Principal principal,@RequestBody BikeDTO dto, HttpServletRequest request) {
        String username = principal.getName();
        return bikeService.reservation(dto, username);
    }
}
