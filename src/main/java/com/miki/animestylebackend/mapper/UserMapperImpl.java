package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.UserDto;
import com.miki.animestylebackend.dto.UserData;
import com.miki.animestylebackend.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user, String message) {
        if (user == null) {
            return null;
        }

        UserData userData = new UserData();
        userData.setId(user.getId());
        userData.setEmail(user.getEmail());
        userData.setFirstname(user.getFirstname());
        userData.setLastname(user.getLastname());
        userData.setPhoneNumber(user.getPhone());
        userData.setAddress(user.getAddress());
        userData.setAvatar(user.getAvatar());
        userData.setRole(user.getRole());

        UserDto userDto = new UserDto();
        userDto.setSuccess(true);
        userDto.setStatus(200);
        userDto.setMessage(message);
        userDto.setData(userData);
        userDto.setTimestamp(LocalDateTime.now());

        return userDto;
    }

    @Override
    public UserData toUserData(User user) {
        if (user == null) {
            return null;
        }

        UserData userData = new UserData();
        userData.setId(user.getId());
        userData.setEmail(user.getEmail());
        userData.setFirstname(user.getFirstname());
        userData.setLastname(user.getLastname());
        userData.setPhoneNumber(user.getPhone());
        userData.setAddress(user.getAddress());
        userData.setAvatar(user.getAvatar());
        userData.setRole(user.getRole());

        return userData;
    }
}