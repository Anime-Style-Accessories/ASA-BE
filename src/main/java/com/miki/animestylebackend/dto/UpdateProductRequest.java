package com.miki.animestylebackend.dto;

import com.miki.animestylebackend.model.ProductColor;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdateProductRequest {
    private UUID id;
    private String name;
    private String description;
    @Enumerated
    private ProductColor color;
    private BigDecimal price;
    private int quantity;
}
