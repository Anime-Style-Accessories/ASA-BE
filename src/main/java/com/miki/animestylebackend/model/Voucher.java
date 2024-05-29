package com.miki.animestylebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vouchers")
public class Voucher extends BaseEntity{

    private String code;
    private Integer discount;
    private LocalDate expirationDate;
    private String title;
    private String description;
    private int quantity;
    private boolean isUsed;
}
