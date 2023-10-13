package com.reserv.reservationsite.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.entity.Role;
import com.reserv.reservationsite.core.entity.isReserved;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    private String username;

    private String password;

    private String phoneNumber;

    @JsonProperty("isVIP")
    private boolean isVIP;

    private LocalDate startDate;

    private LocalDate endDate;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber)
                .isAdmin(Role.USER)
                .isVIP(isVIP)
                .isReserved(isReserved.NOT_RESERVED)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
    public Member toEntityAdmin() {
        return Member.builder()
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber)
                .isAdmin(Role.ADMIN)
                .isVIP(isVIP)
                .isReserved(isReserved.NOT_RESERVED)
                .build();
    }
}
