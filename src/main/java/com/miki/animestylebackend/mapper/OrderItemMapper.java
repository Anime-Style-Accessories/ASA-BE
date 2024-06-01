package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.OrderItemData;
import com.miki.animestylebackend.dto.OrderItemDto;
import com.miki.animestylebackend.model.OrderItem;

public interface OrderItemMapper {
//    OrderItem toOrder(CreateOrderItemRequest createOrderRequest);

    OrderItemDto toOrderItemDto(OrderItem orderItem, String message);
    OrderItemData toOrderItemData(OrderItem orderItem, String message);
}
