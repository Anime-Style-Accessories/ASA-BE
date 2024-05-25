package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.OrderDto;
import com.miki.animestylebackend.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto toOrderDto(Order order) {
        if (order == null) {
            return null;
        }
        OrderDto.UserDto userDto = new OrderDto.UserDto(order.getUser().getUsername(),
                order.getUser().getFirstname(),
                order.getUser().getLastname(),
                order.getUser().getAvatar());
        return new OrderDto(order.getId(),
                userDto,
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getPaymentStatus(),
                order.getShippingStatus());
    }
}
