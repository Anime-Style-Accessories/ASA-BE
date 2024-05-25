package com.miki.animestylebackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVoucherRequest {
    private String code;
    private String title;
    private int discount;
    private int quantity;
    private String description;
}
