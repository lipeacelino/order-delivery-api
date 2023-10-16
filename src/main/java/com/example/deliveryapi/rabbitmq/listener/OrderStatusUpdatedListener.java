package com.example.deliveryapi.rabbitmq.listener;

import com.example.deliveryapi.dto.OrderStatusDTO;
import com.example.deliveryapi.entities.Order;
import com.example.deliveryapi.repositories.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusUpdatedListener {

    @Autowired
    private OrderRepository orderRepository;

    @RabbitListener(queues = "${rabbitmq.order.update-status.queue}")
    private void orderStatusUpdatedListener(OrderStatusDTO orderStatusDTO) {
        Order order = orderRepository.findById(orderStatusDTO.orderId()).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(orderStatusDTO.status());
        orderRepository.save(order);
    }

}
