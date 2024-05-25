package com.miki.animestylebackend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateOrderRequest {
    private BigDecimal totalAmount;
    private String email;
    private String address;
    private List<CreateOrderItemRequest> orderItems;
}
