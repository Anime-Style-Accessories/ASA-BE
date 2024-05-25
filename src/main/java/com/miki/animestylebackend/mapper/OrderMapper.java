package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.OrderDto;
import com.miki.animestylebackend.model.Order;

public interface OrderMapper {
    OrderDto toOrderDto(Order order);
}
