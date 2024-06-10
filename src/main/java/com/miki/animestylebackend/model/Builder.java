package com.miki.animestylebackend.model;

public interface Builder {
    void setFirstname(String firstname);
    void setLastname(String lastname);
    void setEmail(String email);
    void setPassword(String password);
    void setPhone(String phone);
    void setAddress(String address);
    void setAvatar(String avatar);
    void setRole(Role role);
    User build();
}
