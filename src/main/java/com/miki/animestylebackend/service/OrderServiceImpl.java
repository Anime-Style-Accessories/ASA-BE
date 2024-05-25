package com.miki.animestylebackend.service;


import com.miki.animestylebackend.dto.CreateOrderRequest;
import com.miki.animestylebackend.dto.CreateOrderItemRequest;
import com.miki.animestylebackend.dto.OrderDto;
import com.miki.animestylebackend.dto.UserDto;
import com.miki.animestylebackend.exception.OrderNotFoundException;
import com.miki.animestylebackend.mapper.OrderMapper;
import com.miki.animestylebackend.mapper.UserMapper;
import com.miki.animestylebackend.model.Order;
import com.miki.animestylebackend.model.OrderItem;
import com.miki.animestylebackend.model.Product;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.repository.OrderItemRepository;
import com.miki.animestylebackend.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final UserMapper userMapper;
    private final UserService userService;
    private final OrderMapper orderMapper;

    private EntityManager entityManager;

    @Override
    public List<Order> findAllOrdersSortedByDateDescending() {
        String jpql = "SELECT o FROM Order o ORDER BY o.orderDate DESC";
        TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class);
        return query.getResultList();
    }

    @Override
    public List<Order> viewAll() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    @Override
    public List<Order> findByShippingStatus(String shippingStatus) {
        return orderRepository.findByShippingStatus(shippingStatus);
    }

    @Override
    public List<Order> findByPaymentStatus(String shippingStatus) {
        return orderRepository.findByPaymentStatus(shippingStatus);
    }

    @Override
    @Transactional
    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {
        // Create a new Order object
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentStatus("PENDING");
        order.setShippingStatus("PENDING");
        order.setShippingAddress(createOrderRequest.getAddress());


        // Validate and get the user by email
        User user = userService.getUserByUsername(createOrderRequest.getEmail());
        order.setUser(user);


        // Calculate the total amount and create order items
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CreateOrderItemRequest item : createOrderRequest.getOrderItems()) {
            Product product = productService.getProductById(item.getProductId());
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .order(order)
                    .quantity(item.getQuantity())
                    .pricePerUnit(item.getPricePerUnit())
                    .size(item.getSize())
                    .color(item.getColor())
                    .voucherValue(item.getVoucher())
                    .shippingValue(item.getShipping())
                    .build();

            totalAmount = totalAmount.add(item.getPricePerUnit().multiply(new BigDecimal(item.getQuantity())));
            orderItemRepository.save(orderItem);

            log.info("Adding order item: {}", orderItem);
        }

        // Set the total amount in the order
        order.setTotalAmount(totalAmount);

        // Log the fully constructed order before saving
        log.info("Order created: {}", order);

        // Save the order to the database
        return orderMapper.toOrderDto(orderRepository.save(order));

        // Log the order after it has been saved

    }


    @Override
    public Order findById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    public List<Order> findByUserId(UUID id) {
        return orderRepository.findByUserId(id);
    }


    @Override
    public List<Order> getOrdersByUser(String email) {
        return orderRepository.findByUserEmailContaining(email);
    }

//    @Override
//    public List<DailyRevenueDTO> calculateDailyRevenue() {
//        List<Order> orders = orderRepository.findAll();
//
//        List<Order> sortedOrders = orders.stream()
//                .sorted(Comparator.comparing(Order::getOrderDate))
//                .distinct()
//                .collect(Collectors.toList());
//
//
//        List<Order> recentOrders = sortedOrders.stream()
//                .limit(28)
//                .toList();
//
//        Map<LocalDate, BigDecimal> revenueByDate = recentOrders.stream()
//                .collect(Collectors.groupingBy(
//                        order -> order.getOrderDate().toLocalDate(),
//                        LinkedHashMap::new,
//                        Collectors.mapping(
//                                Order::getTotalAmount,
//                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
//                        )
//                ));
//
//
//        List<DailyRevenueDTO> dailyRevenues = revenueByDate.entrySet().stream()
//                .map(entry -> {
//                    DailyRevenueDTO dailyRevenue = new DailyRevenueDTO();
//                    dailyRevenue.setDate(entry.getKey());
//                    dailyRevenue.setRevenue(entry.getValue());
//                    return dailyRevenue;
//                })
//                .collect(Collectors.toList());
//
//        return dailyRevenues;
//
//    }



    @Override
    public BigDecimal calculateTotalRevenue() {
        return orderRepository.findAll().stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<Order> getOrdersContainingText(String text) {
        User user = userService.getUserByUsername(text);
        return orderRepository.findByUser(user);
    }

    @Override
    public List<Order> getOrdersByUserName(String userName) {
        return orderRepository.findByUserEmailContaining(userName);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}