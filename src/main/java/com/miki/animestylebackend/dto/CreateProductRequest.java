package com.miki.animestylebackend.dto;

import com.miki.animestylebackend.model.ProductColor;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateProductRequest {
    private String name;
    private String category;
    private String description;
    @Enumerated
    private ProductColor color;
    private BigDecimal price;
    private int quantity;
    private String image;
    private String size;
}
