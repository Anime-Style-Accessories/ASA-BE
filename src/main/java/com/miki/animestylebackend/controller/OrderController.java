package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.CreateOrderRequest;
import com.miki.animestylebackend.dto.OrderDto;
import com.miki.animestylebackend.model.Order;
import com.miki.animestylebackend.service.OrderService;
import com.miki.animestylebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/create_order")
    public OrderDto checkout(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createOrder(createOrderRequest);
    }

    @GetMapping("/countOrders")
    public ResponseEntity<Integer> countProducts() {
        return ResponseEntity.ok(orderService.viewAll().size());
    }

//    @GetMapping("/getOrders")
//    public ResponseEntity<List<Order>> findOrdersByUserId(@RequestParam("userId") int id,
//                                                   @RequestParam("shippingAddress") String address,
//                                                   @RequestParam("firstName") String firstName,
//                                                   @RequestParam("lastName") String lastName,
//                                                   @RequestParam("email") String email,
//                                                   @RequestParam("phoneNumber") String phone) {
//        User user = new User(id,email,firstName,lastName,phone,address);
//
//        List<Order> orders = orderService.getOrdersByUser(user);
//        return ResponseEntity.ok(orders);
//    }
}
