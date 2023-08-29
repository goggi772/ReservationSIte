package com.reserv.reservationsite.DTO;

import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.entity.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    private Long id;

    private String userid;

    private String name;

    private String password;

    private Role role;

    public Member toEntity() {
        return Member.builder()
                .userid(userid)
                .name(name)
                .password(password)
                .role(Role.USER)
                .build();
    }
}
