package com.miki.animestylebackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
public class OrderData {
    private UUID id;
    private UserData user;
    private LocalDateTime createdAt;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private String shippingStatus;
}
