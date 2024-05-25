package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.OrderItemDto;
import com.miki.animestylebackend.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapperImpl implements OrderItemMapper {

    @Override
    public OrderItemDto toOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        OrderItemDto.ProductDto productDto = new OrderItemDto.ProductDto(orderItem.getProduct().getProductName(), orderItem.getProduct().getProductImage());
        return new OrderItemDto(orderItem.getId(), productDto, orderItem.getQuantity(), orderItem.getPricePerUnit(), orderItem.getVoucherValue(), orderItem.getShippingValue(), orderItem.getSize(), orderItem.getColor());
    }

//    @Component
//    public class OrderItemMapper {
//        public OrderItemDto toDTO(OrderItem orderItem) {
//            OrderItemDto dto = new OrderItemDto();
//            dto.setId(orderItem.getId());
//            dto.setProductId(orderItem.getProduct().getId());
//            dto.setProductName(orderItem.getProduct().getName());
//            dto.setQuantity(orderItem.getQuantity());
//            dto.setPricePerUnit(orderItem.getPricePerUnit());
//            return dto;
//        }
//    }

}
