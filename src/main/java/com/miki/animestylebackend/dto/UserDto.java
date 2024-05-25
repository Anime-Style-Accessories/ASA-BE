package com.miki.animestylebackend.dto;

import com.miki.animestylebackend.model.Role;

import java.util.UUID;

public record UserDto(UUID id, String email, String firstname, String lastname, String phoneNumber, String address, String avatar, Role role) {
    public record OrderDto(String id, String orderStatus) {
    }
}