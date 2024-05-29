package com.miki.animestylebackend.dto;

import com.miki.animestylebackend.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class UserData {
    private UUID id;
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String address;
    private String avatar;
    private Role role;
}
