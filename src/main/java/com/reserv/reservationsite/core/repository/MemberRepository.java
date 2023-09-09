package com.reserv.reservationsite.core.repository;

import com.reserv.reservationsite.DTO.MemberDTO;
import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.isReserved = :reserved WHERE m.username IN (SELECT b.owner FROM Bike b)")
    int updateAllByIsReserved(@Param("reserved") boolean reserved);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.isReserved = false WHERE m.isReserved = true")
    int resetIsReserved();

}
