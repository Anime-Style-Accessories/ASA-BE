package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.CreateOrderRequest;
import com.miki.animestylebackend.dto.OrderData;
import com.miki.animestylebackend.dto.OrderDto;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.model.Order;
import com.miki.animestylebackend.service.OrderService;
import com.miki.animestylebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController extends BaseController{
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/create_order")
    public ResponseEntity<OrderDto> checkout(@RequestBody CreateOrderRequest createOrderRequest) {
        return ResponseEntity.ok(orderService.createOrder(createOrderRequest));
    }

    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.findById(orderId));
    }

    @GetMapping("/getOrders")
    public ResponseEntity<PageData<OrderData>> getOrders(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.getALl(page, size));
    }

    @GetMapping("/countOrders")
    public ResponseEntity<Integer> countProducts() {
        return ResponseEntity.ok(orderService.viewAll().size());
    }

    @GetMapping("/getOrdersByUserId/{userEmail}")
    public ResponseEntity<PageData<OrderData>> getOrdersByUserId(@PathVariable String userEmail,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.findOrderByUserEmail(userEmail, page, size));
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
