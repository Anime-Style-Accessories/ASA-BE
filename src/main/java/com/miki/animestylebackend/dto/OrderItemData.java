package com.miki.animestylebackend.dto;

import com.miki.animestylebackend.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
@Setter
public class OrderItemData {
    private UUID id;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private String size;
    private String color;
    private Product productData;
}
