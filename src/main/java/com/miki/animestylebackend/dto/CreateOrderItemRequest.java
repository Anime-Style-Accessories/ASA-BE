package com.miki.animestylebackend.dto;

import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateOrderItemRequest {
    private UUID productId;
    private int quantity;
    private BigDecimal pricePerUnit;
    private String size;
    private String color;
    private int voucher;
    private int shipping;
}
