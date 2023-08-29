package com.reserv.reservationsite.core.entity;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    @Id
    @Column
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;

    public void seat_reserv(Member member) {
        this.member = member;
    }
}
