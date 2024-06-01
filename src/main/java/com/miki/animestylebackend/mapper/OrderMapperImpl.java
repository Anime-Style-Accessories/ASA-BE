package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.OrderDto;
import com.miki.animestylebackend.dto.OrderData;
import com.miki.animestylebackend.dto.UserData;
import com.miki.animestylebackend.dto.UserDto;
import com.miki.animestylebackend.model.Order;
import com.miki.animestylebackend.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto toOrderDto(Order order, String message) {
        if (order == null) {
            return null;
        }

        OrderData orderData = new OrderData();
        orderData.setId(order.getId());
        orderData.setUser(order.getUserEmail());
        orderData.setCreatedAt(order.getOrderDate());
        orderData.setTotalAmount(order.getTotalAmount());
        orderData.setPaymentStatus(order.getPaymentStatus());
        orderData.setShippingStatus(order.getShippingStatus());

        OrderDto orderDto = new OrderDto();
        orderDto.setSuccess(true);
        orderDto.setStatus(200);
        orderDto.setMessage(message);
        orderDto.setData(orderData);
        orderDto.setTimestamp(LocalDateTime.now());

        return orderDto;
    }

    @Override
    public OrderData toOrderData(Order order) {
        if (order == null) {
            return null;
        }

        OrderData orderData = new OrderData();
        orderData.setId(order.getId());
        orderData.setUser(order.getUserEmail());
        orderData.setCreatedAt(order.getOrderDate());
        orderData.setTotalAmount(order.getTotalAmount());
        orderData.setPaymentStatus(order.getPaymentStatus());
        orderData.setShippingStatus(order.getShippingStatus());

        return orderData;
    }

    private UserData toUserDto(User user) {
        if (user == null) {
            return null;
        }
        UserData userDto = new UserData();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setPhoneNumber(user.getPhone());
        userDto.setAddress(user.getAddress());
        userDto.setAvatar(user.getAvatar());
        userDto.setRole(user.getRole());
        return userDto;
    }
}