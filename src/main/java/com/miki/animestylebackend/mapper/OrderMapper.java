package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.OrderData;
import com.miki.animestylebackend.dto.OrderDto;
import com.miki.animestylebackend.model.Order;

public interface OrderMapper {
    OrderDto toOrderDto(Order order, String message);

    OrderData toOrderData(Order order);
}
