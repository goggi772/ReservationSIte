package com.reserv.reservationsite.core.repository;

import com.reserv.reservationsite.core.entity.Seat;
import com.reserv.reservationsite.core.entity.SeatStatus;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    Optional<Seat> findById(Long id);
}
