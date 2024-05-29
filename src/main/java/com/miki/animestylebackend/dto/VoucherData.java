package com.miki.animestylebackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;
@Getter
@Setter
public class VoucherData {
    private UUID id;
    private String code;
    private Integer discount;
    private LocalDate expirationDate;
    private String description;
    private String title;
    private int quantity;

}
