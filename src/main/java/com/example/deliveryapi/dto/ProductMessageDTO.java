package com.example.deliveryapi.dto;

import java.math.BigDecimal;

public record ProductMessageDTO(
        Long id,
        String name,
        BigDecimal price
) {
}
