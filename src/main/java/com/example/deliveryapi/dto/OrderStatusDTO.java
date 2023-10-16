package com.example.deliveryapi.dto;

import com.example.deliveryapi.enumerators.Status;
import lombok.Builder;

@Builder
public record OrderStatusDTO(
        Long orderId,
        Status status
) {
}
