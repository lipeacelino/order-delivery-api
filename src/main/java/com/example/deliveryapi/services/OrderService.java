package com.example.deliveryapi.services;

import com.example.deliveryapi.dto.OrderInputDTO;
import com.example.deliveryapi.dto.OrderStatusDTO;
import com.example.deliveryapi.entities.Order;
import com.example.deliveryapi.entities.OrderItem;
import com.example.deliveryapi.entities.Product;
import com.example.deliveryapi.enumerators.Status;
import com.example.deliveryapi.rabbitmq.publisher.OrderCreatedPublisher;
import com.example.deliveryapi.repositories.OrderItemRepository;
import com.example.deliveryapi.repositories.OrderRepository;
import com.example.deliveryapi.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderCreatedPublisher orderCreatedPublisher;

    public Order createOrder(OrderInputDTO orderInputDTO) {
        List<OrderItem> orderItemsToSave = new ArrayList<>();
        AtomicReference<BigDecimal> totalOrder = new AtomicReference<>(BigDecimal.ZERO);

        orderInputDTO.orderItemInputDTOList().forEach(orderItemInputDTO -> {
            Product product = productRepository.findById(orderItemInputDTO.productId()).orElseThrow(() -> new RuntimeException("Product not found"));
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(orderItemInputDTO.quantity())
                    .total(BigDecimal.valueOf(orderItemInputDTO.quantity()).multiply(product.getPrice()))
                    .build();
            orderItemsToSave.add(orderItem);
            totalOrder.set(totalOrder.get().add(orderItem.getTotal()));
        });

        Order orderToSave = Order.builder()
                .orderItems(orderItemsToSave)
                .status(Status.APPROVED)
                .total(totalOrder.get()).build();

        orderItemsToSave.forEach(orderItem -> {
            orderItem.setOrder(orderToSave);
        });

        Order orderSaved = orderRepository.save(orderToSave);

        OrderStatusDTO orderStatusDTO = OrderStatusDTO.builder()
                .orderId(orderSaved.getId())
                .status(orderSaved.getStatus())
                .build();

        orderCreatedPublisher.send(orderStatusDTO);

        return orderSaved;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
