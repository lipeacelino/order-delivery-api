package com.example.deliveryapi.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductAddedConfig {

    @Value("${rabbitmq.product.created.exchange}")
    private String exchange;

    @Value("${rabbitmq.product.created.exchange-dlx}")
    private String exchangeDlx;

    @Value("${rabbitmq.product.created.queue}")
    private String queue;

    @Value("${rabbitmq.product.created.queue-dlq}")
    private String queueDlq;

    @Value("${rabbitmq.product.created.routing.key}")
    private String bindingKey;

    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchange).durable(false).autoDelete().build();
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable(queue).deadLetterExchange(exchangeDlx).build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(bindingKey);
    }

    @Bean
    public DirectExchange directExchangeDlx() {
        return ExchangeBuilder.directExchange(exchangeDlx).durable(false).autoDelete().build();
    }

    @Bean
    public Queue queueDlq() {
        return QueueBuilder.nonDurable(queueDlq).build();
    }

    @Bean
    public Binding bindingDlx() {
        return BindingBuilder.bind(queueDlq()).to(directExchangeDlx()).with(bindingKey);
    }

}