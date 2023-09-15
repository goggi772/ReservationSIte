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

    private String phoneNumber;

    private boolean isReserved;

    private boolean isVIP;

    public MemberDTO(Member member) {
        this.username = member.getUsername();
        this.phoneNumber = member.getPhoneNumber();
        this.isReserved = member.isReserved();
        this.isVIP = member.isVIP();
    }
}
