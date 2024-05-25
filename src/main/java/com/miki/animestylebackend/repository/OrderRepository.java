package com.miki.animestylebackend.repository;


import com.miki.animestylebackend.model.Order;
import com.miki.animestylebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByUserEmailContaining(String email);
    List<Order> findByUser(User user);
    Optional<Order> findById(UUID id);
    List<Order> findByUserId(UUID id);
    List<Order> findByShippingStatus(String shippingStatus);
    List<Order> findByPaymentStatus(String paymentStatus);
    List<Order> findAllByOrderByOrderDateDesc();
}

