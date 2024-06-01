package com.miki.animestylebackend.service;


import com.miki.animestylebackend.dto.*;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.exception.OrderNotFoundException;
import com.miki.animestylebackend.exception.VoucherNotFoundException;
import com.miki.animestylebackend.mapper.OrderMapper;
import com.miki.animestylebackend.mapper.UserMapper;
import com.miki.animestylebackend.model.*;
import com.miki.animestylebackend.repository.OrderItemRepository;
import com.miki.animestylebackend.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final VoucherService voucherService;
    private final EmailService emailService;
    private EntityManager entityManager;

    @Override
    public List<Order> findAllOrdersSortedByDateDescending() {
        String jpql = "SELECT o FROM Order o ORDER BY o.orderDate DESC";
        TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class);
        return query.getResultList();
    }

    @Override
    public PageData<OrderData> getALl(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderData> orderDtoPage = orderRepository.findAll(pageable).map(orderMapper::toOrderData);

        return new PageData<>(orderDtoPage, "Orders found successfully");
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
        int discountPercentage = 0;
        if(!createOrderRequest.getVoucherCode().isEmpty()) {
            Voucher voucher = voucherService.getVoucherByCode(createOrderRequest.getVoucherCode());
            if(voucher == null) {
                throw new VoucherNotFoundException("Voucher not found");
            }
            voucherService.useVoucher(voucher);
            discountPercentage = voucher.getDiscount();
        }
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentStatus("PENDING");
        order.setShippingStatus("PENDING");
        order.setShippingAddress(createOrderRequest.getAddress());
        order.setVoucherCode(createOrderRequest.getVoucherCode());
        order.setUserEmail(createOrderRequest.getEmail());
        order.setPaymentMethod(createOrderRequest.getPaymentMethod());
//        User user = userService.getUserByUsername(createOrderRequest.getEmail());
//        order.setUser(user);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CreateOrderItemRequest item : createOrderRequest.getOrderItems()) {
            Product product = productService.getProductById(item.getProductId());
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .order(order)
                    .quantity(item.getQuantity())
                    .pricePerUnit(product.getProductPrice())
                    .size(item.getSize())
                    .color(item.getColor())
                    .build();

            totalAmount = totalAmount.add(product.getProductPrice().multiply(new BigDecimal(item.getQuantity())));
            orderItemRepository.save(orderItem);

            log.info("Adding order item: {}", orderItem);
        }

        totalAmount = totalAmount.subtract(totalAmount.multiply(new BigDecimal(discountPercentage)).divide(new BigDecimal(100)));

        order.setTotalAmount(totalAmount);

        log.info("Order created: {}", order);

        OrderDto orderDto = orderMapper.toOrderDto(orderRepository.save(order), "Order created successfully");

        sendCustomerOrderDetailLinkEmail(createOrderRequest.getEmail(), "Order created successfully", order.getId().toString());
        sendAdminOrderDetailLinkEmail("radiomfmdak@gmail.com", "Order created successfully", order.getId().toString());

        return orderDto;
    }

    private void sendCustomerOrderDetailLinkEmail(String email, String subjectEmail, String order_id) {
        String text = "Your order has been created successfully, here are you links to track your order:" +
                "http://localhost:5174/order/" + order_id;

        emailService.sendEmail(email, subjectEmail, text);
    }

    private void sendAdminOrderDetailLinkEmail(String email, String subjectEmail, String order_id) {
        String text = "Your order has been created successfully, here are you links to track your order:" +
                "http://localhost:5173/order/" + order_id;

        emailService.sendEmail(email, subjectEmail, text);
    }




    @Override
    public OrderDto findById(UUID id) {
        return orderRepository.findById(id).map(order -> orderMapper.toOrderDto(order, "Order found successfully"))
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
    }

//    @Override
//    public PageData<OrderData> findOrderByUserId(UUID id, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<OrderData> orderDtoPage = orderRepository.findByUserId(id, pageable).map(orderMapper::toOrderData);
//
//        return new PageData<>(orderDtoPage, "Orders found successfully");
//    }

    @Override
    public PageData<OrderData> findOrderByUserEmail(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderData> orderDtoPage = orderRepository.findByUserEmailIgnoreCase(email, pageable).map(orderMapper::toOrderData);

        return new PageData<>(orderDtoPage, "Orders found successfully");
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

//    @Override
//    public List<Order> getOrdersContainingText(String text) {
//        User user = userService.getUserByUsername(text);
//        return orderRepository.findByUser(user);
//    }

    @Override
    public List<Order> getOrdersByUserName(String userName) {
        return orderRepository.findByUserEmailContaining(userName);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public OrderDto updateOrderStatus(UUID uuid, UpdateStatusRequest updateStatusRequest) {
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + uuid + " not found"));

        order.setShippingStatus(updateStatusRequest.getDeliveryStatus());
        order.setPaymentStatus(updateStatusRequest.getPaymentStatus());

        return orderMapper.toOrderDto(orderRepository.save(order), "Order status updated successfully");
    }

    @Override
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }

}
