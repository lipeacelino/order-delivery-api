package com.example.deliveryapi.dto;

public record OrderItemInputDTO(
        Long productId,
        Integer quantity
) {
}
