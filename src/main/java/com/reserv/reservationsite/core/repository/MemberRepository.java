package com.reserv.reservationsite.core.repository;

import com.reserv.reservationsite.core.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserid(String userid);
}
