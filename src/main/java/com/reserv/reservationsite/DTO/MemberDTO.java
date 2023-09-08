package com.reserv.reservationsite.DTO;

import com.reserv.reservationsite.core.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberDTO {

    private String username;

    private String name;

    public MemberDTO(Member member) {
        this.username = member.getUsername();
    }
}
