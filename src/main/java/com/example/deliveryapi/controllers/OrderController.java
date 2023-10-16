package com.example.deliveryapi.controllers;

import com.example.deliveryapi.dto.OrderInputDTO;
import com.example.deliveryapi.entities.Order;
import com.example.deliveryapi.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Order createOrder(@RequestBody OrderInputDTO orderInputDTO) {
        return orderService.createOrder(orderInputDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

}
