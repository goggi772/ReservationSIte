package com.reserv.reservationsite.core.repository;

import com.reserv.reservationsite.DTO.BikeDTO;
import com.reserv.reservationsite.core.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BikeRepository extends JpaRepository<Bike, Long> {

    Optional<Bike> findById(Long id);

//    @Query("SELECT new com.reserv.reservationsite.DTO.BikeDTO(b.id, b.status, b.member.username) FROM Bike b ORDER BY b.id ASC")
//    List<BikeDTO> findAllBikes();
    List<Bike> findAllByOrderByIdAsc();


}
