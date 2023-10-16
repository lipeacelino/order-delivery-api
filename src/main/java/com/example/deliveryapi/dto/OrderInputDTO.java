package com.example.deliveryapi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record OrderInputDTO(
        @JsonAlias("orderItems")
        List<OrderItemInputDTO> orderItemInputDTOList
) {
}
