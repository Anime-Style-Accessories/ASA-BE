package com.miki.animestylebackend.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateVoucherRequest {
    private String code;
    private String title;
    private int discount;
    private int quantity;
    private String description;
    private LocalDate expirationDate;
}
