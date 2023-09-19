package com.reserv.reservationsite.core.repository;

import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.entity.isReserved;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.isReserved = :reserved WHERE m.username IN (SELECT b.owner FROM Bike b)")
    int updateAllByIsReserved(@Param("reserved") isReserved reserved);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.isReserved = 'NOT_RESERVED' WHERE m.isReserved != 'NOT_RESERVED'")
    int resetIsReserved();

    @Override
    List<Member> findAll(Sort sort);


    void deleteByUsername(String username);

}
