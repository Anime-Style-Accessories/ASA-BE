package com.miki.animestylebackend.repository;

import com.miki.animestylebackend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    // Additional custom query methods can be added here
    List<OrderItem> findByOrderId(UUID id);
}

