package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.OrderItemData;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.service.OrderItemService;
import com.miki.animestylebackend.service.OrderService;
import com.miki.animestylebackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order_items")
public class OrderItemController extends BaseController{
    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final ProductService productService;

//    @PostMapping("/createItem")
//    public ResponseEntity<String> createOrderItem(@RequestBody CreateOrderItemRequest createOrderItemRequest) {
//
//        Product product = productService.getProductById(createOrderItemRequest.getProductId());
//
//        OrderItem orderItem = OrderItem.builder()
//                .product(product)
//                .order(order)
//                .quantity(createOrderItemRequest.getQuantity())
//                .pricePerUnit(createOrderItemRequest.getPricePerUnit())
//                .size(createOrderItemRequest.getSize())
//                .color(createOrderItemRequest.getColor())
//                .voucherValue(createOrderItemRequest.getVoucher())
//                .shippingValue(createOrderItemRequest.getShipping())
//                .build();
//
//        orderItemService.createOrderItem(orderItem);
//
//        return ResponseEntity.ok("Thành công!");
        // Gọi service để tạo đơn hàng mới và lưu các mục trong giỏ hàng
//        Order order = orderService.createOrderFromCartItems(cartItems, user);

        // Trả về thông báo thành công hoặc thất bại
//        if (order != null) {
//            return ResponseEntity.ok("Đơn hàng đã được tạo thành công.");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi tạo đơn hàng.");
//        }
//        return null;
//    }

    @GetMapping("/{id}")
    public ResponseEntity<PageData<OrderItemData>> getOrderItemByOrderId(@PathVariable UUID id,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderItemService.getOrderItemByOrderId(id, page, size));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteOrderItemById(@PathVariable Long id) {
//        orderItemService.deleteOrderItemById(id);
//        return ResponseEntity.ok("OrderItem deleted successfully");
//    }
}
