package com.reserv.reservationsite.core.repository;

import com.reserv.reservationsite.DTO.BikeDTO;
import com.reserv.reservationsite.core.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Bike, Long> {

    Optional<Bike> findById(Long id);


}
