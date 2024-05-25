package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.UserDto;
import com.miki.animestylebackend.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
//       List<UserDto.OrderDto> orders = user.getOrders().stream().map(this::toUserDtoOrderDto).toList();
        return new UserDto(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getPhone(), user.getAddress(), user.getAvatar(), user.getRole());
    }
}
