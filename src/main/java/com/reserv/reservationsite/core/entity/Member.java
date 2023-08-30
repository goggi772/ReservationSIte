package com.reserv.reservationsite.core.entity;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5)
    private String name;

    @Column(nullable = false, length = 15, unique = true)
    private String username;

    @Column(nullable = false, length = 20)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role isAdmin;

    @Column(nullable = false)
    private boolean isReserved;

    public void modi_username(String name) {
        this.name = name;
    }

    public void reset_pass(String password) {
        this.password = password;
    }

}
