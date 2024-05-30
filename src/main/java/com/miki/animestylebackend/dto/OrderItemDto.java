package com.miki.animestylebackend.dto;

import java.math.BigDecimal;

public record OrderItemDto(java.util.UUID id, ProductDto product, Integer quantity, BigDecimal pricePerUnit, String size, String color) {

    public record ProductDto(String name, String avatar) {
    }
}
