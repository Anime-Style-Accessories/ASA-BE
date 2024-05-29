package com.miki.animestylebackend.dto;

import java.time.LocalDate;
import java.util.UUID;

public record VoucherDto(UUID id, String code, Integer discount, LocalDate expirationDate, String description, String title, int quantity) {
}
