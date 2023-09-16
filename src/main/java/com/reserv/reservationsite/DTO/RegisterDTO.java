package com.reserv.reservationsite.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.entity.Role;
import lombok.*;

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

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber)
                .isAdmin(Role.USER)
                .isVIP(isVIP)
                .isReserved(false)
                .build();
    }
}
