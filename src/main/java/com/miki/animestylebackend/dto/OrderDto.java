package com.miki.animestylebackend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderDto(UUID id, UserDto user, LocalDateTime createdAt, BigDecimal totalAmount, String paymentStatus, String shippingStatus) {

    public record UserDto(String username, String firstName, String lastName, String avatar) {
    }
}