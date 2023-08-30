package com.reserv.reservationsite.core.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BikeStatus status;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;

    public void seat_reserv(Member member) {
        this.member = member;
    }
}
