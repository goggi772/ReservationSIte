package com.reserv.reservationsite.DTO;

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

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .isAdmin(Role.USER)
                .build();
    }
}
