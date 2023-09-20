package com.reserv.reservationsite.DTO;

import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.entity.isReserved;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
public class MemberDTO {

    private String username;

    private String phoneNumber;

    private String reserved;

    private String reservedTime;

    private boolean isVIP;

    public MemberDTO(Member member) {
        this.username = member.getUsername();
        this.phoneNumber = member.getPhoneNumber();
        this.reserved = member.getIsReserved().getMessage();
        this.isVIP = member.isVIP();

        if (member.getReservedTime() != null) {
            this.reservedTime = member.getReservedTime().format(DateTimeFormatter.ofPattern("HH : mm : ss"));
        } else {
            this.reservedTime = "-- : -- : --";
        }
    }
}
