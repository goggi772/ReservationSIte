package com.reserv.reservationsite.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageDTO {

    private Integer pageNo;

    private Integer pageSize;
}
