package com.reserv.reservationsite.core.entity;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15, unique = true)
    private String username;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private isReserved isReserved;  //예약을 적어도 한번 했는지

    @Column
    private LocalDateTime reservedTime;

    @Column(nullable = false)
    private boolean isVIP;       //예약을 여러번 할 수 있는지(VIP면 예약을 여러번할 수 있고 아니면 한번밖에 못함)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role isAdmin;

    public void reset_change_pass(String password) {
        this.password = password;
    }

    public void set_reserved_time(LocalDateTime time) {
        this.reservedTime = time;
    }



}
