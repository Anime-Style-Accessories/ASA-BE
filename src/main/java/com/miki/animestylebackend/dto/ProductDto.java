package com.miki.animestylebackend.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDto(UUID id, String name, String image, CategoryDto categoryDto, String description, BigDecimal price) {

    public record CategoryDto(String name) {
    }
}
