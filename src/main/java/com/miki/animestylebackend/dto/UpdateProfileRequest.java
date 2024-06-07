package com.miki.animestylebackend.dto;

import com.miki.animestylebackend.model.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdateProfileRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String avatar;
    private Role role;
}
