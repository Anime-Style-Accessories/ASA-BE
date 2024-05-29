package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.UserData;
import com.miki.animestylebackend.dto.UserDto;
import com.miki.animestylebackend.model.User;

public interface UserMapper {

    UserDto toUserDto(User user, String message);
    UserData toUserData(User user);
}