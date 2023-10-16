package com.example.deliveryapi.rabbitmq.listener;

import com.example.deliveryapi.dto.ProductMessageDTO;
import com.example.deliveryapi.entities.Product;
import com.example.deliveryapi.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductCreatedListener {

    @Autowired
    private ProductRepository productRepository;

    @RabbitListener(queues = "${rabbitmq.product.created.queue}")
    private void productListener(ProductMessageDTO productMessageDTO) {
        //Ao cair nessa exceção o rabbit vai fazer o retry até finalmente cair na dlq
        if (productMessageDTO.name().equalsIgnoreCase("Palavra Proíbida")) {
            throw new RuntimeException("Palavra não permitida");
        }
        Product productSaved = productRepository.save(Product.builder()
                .id(productMessageDTO.id())
                .price(productMessageDTO.price())
                .name(productMessageDTO.name())
                .build());
        log.info("Saved productMessageDTO: {}", productSaved);
    }

}
