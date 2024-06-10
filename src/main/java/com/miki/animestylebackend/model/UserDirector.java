package com.miki.animestylebackend.model;

import org.springframework.stereotype.Service;

public class UserDirector {
    public void constructCustomer(Builder builder, String firstname, String lastname, String email, String password) {
        builder.setFirstname(firstname);
        builder.setLastname(lastname);
        builder.setEmail(email);
        builder.setPassword(password);
        builder.setRole(Role.CUSTOMER);
    }

    public void constructAdmin(Builder builder, String firstname, String lastname, String email, String password, String phone, String address, String avatar) {
        builder.setFirstname(firstname);
        builder.setLastname(lastname);
        builder.setEmail(email);
        builder.setPassword(password);
        builder.setPhone(phone);
        builder.setAddress(address);
        builder.setAvatar(avatar);
        builder.setRole(Role.ADMIN);
    }

    public void constructStaff(Builder builder, String firstname, String lastname, String email, String password, String phone, String address, String avatar) {
        builder.setFirstname(firstname);
        builder.setLastname(lastname);
        builder.setEmail(email);
        builder.setPassword(password);
        builder.setPhone(phone);
        builder.setAddress(address);
        builder.setAvatar(avatar);
        builder.setRole(Role.STAFF);
    }
}
