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

    @Column(unique = true)
    private String owner;

    public void seat_reserv(String owner) {
        this.status = BikeStatus.completed;
        this.owner = owner;
    }

    public void cancel_reserv() {
        this.status = BikeStatus.available;
        this.owner = null;
    }
}
