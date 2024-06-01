package com.miki.animestylebackend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdateStatusRequest {
    private String paymentStatus;
    private String deliveryStatus;
}
