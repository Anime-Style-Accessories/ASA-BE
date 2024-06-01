package com.miki.animestylebackend.model;

import jakarta.persistence.*;
import jdk.jfr.Relational;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private String shippingStatus;
    private String shippingAddress;
    private String voucherCode;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
    private String userEmail;
    private PaymentMethod paymentMethod;
}

