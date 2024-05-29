package com.miki.animestylebackend.service;



import com.miki.animestylebackend.dto.CreateOrderRequest;
import com.miki.animestylebackend.dto.OrderData;
import com.miki.animestylebackend.dto.OrderDto;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.model.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<Order> findAllOrdersSortedByDateDescending();

    List<Order> viewAll();

    List<Order> findByShippingStatus(String shippingStatus);
    List<Order> findByPaymentStatus(String shippingStatus);

    OrderDto createOrder(CreateOrderRequest createOrderRequest);
    OrderDto findById(UUID id);
    PageData<OrderData> findOrderByUserId(UUID id, int page, int size);
//    Order createOrderFromCartItems(List<Product> cartItems, User user);
    List<Order> getOrdersByUser(String email);

//    List<DailyRevenueDTO> calculateDailyRevenue();

    BigDecimal calculateTotalRevenue();

    List<Order> getOrdersContainingText(String text);

    List<Order> getOrdersByUserName(String userName);

    void saveOrder(Order order);

}
