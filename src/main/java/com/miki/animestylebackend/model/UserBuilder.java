package com.miki.animestylebackend.model;

public class UserBuilder implements Builder{
    User user;
    public UserBuilder() {
        user = new User();
    }
    @Override
    public void setFirstname(String firstname) {
        user.setFirstname(firstname);
    }

    @Override
    public void setLastname(String lastname) {
        user.setLastname(lastname);
    }

    @Override
    public void setEmail(String email) {
        user.setEmail(email);
    }

    @Override
    public void setPassword(String password) {
        user.setPassword(password);
    }

    @Override
    public void setPhone(String phone) {
        user.setPhone(phone);
    }

    @Override
    public void setAddress(String address) {
        user.setAddress(address);
    }

    @Override
    public void setAvatar(String avatar) {
        user.setAvatar(avatar);
    }

    @Override
    public void setRole(Role role) {
        user.setRole(role);
    }

    @Override
    public User build() {
        return user;
    }
}
