package com.miki.animestylebackend.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateVoucherRequest {
    private String oldCode;
    private String code;
    private String title;
    private int discount;
    private int quantity;
    private String description;

}
