package com.reserv.reservationsite.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePwDTO {

    private String oldPassword;

    private String newPassword;

}
