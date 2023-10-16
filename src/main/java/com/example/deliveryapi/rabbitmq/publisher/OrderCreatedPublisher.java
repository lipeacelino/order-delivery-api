package com.example.deliveryapi.rabbitmq.publisher;

import com.example.deliveryapi.dto.OrderStatusDTO;
import com.example.deliveryapi.entities.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.order.created.exchange}")
    private String exchange;

    @Value("${rabbitmq.order.created.routing.key}")
    private String routingKey;

    public void send(OrderStatusDTO orderStatusDTO) {
        rabbitTemplate.convertAndSend(exchange, routingKey, orderStatusDTO);
    }

}
