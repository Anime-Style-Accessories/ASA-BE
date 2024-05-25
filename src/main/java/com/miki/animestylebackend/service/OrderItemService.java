package com.miki.animestylebackend.service;


import com.miki.animestylebackend.model.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderItemService {
    OrderItem createOrderItem(OrderItem orderItem);
    List<OrderItem> findByOrderId(UUID id);
}
