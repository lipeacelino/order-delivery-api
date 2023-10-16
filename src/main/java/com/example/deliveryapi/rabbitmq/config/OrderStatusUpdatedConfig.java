package com.example.deliveryapi.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderStatusUpdatedConfig {

    @Value("${rabbitmq.order.update-status.exchange}")
    private String exchange;

    @Value("${rabbitmq.order.update-status.queue}")
    private String queue;

    @Value("${rabbitmq.order.update-status.exchange-dlx}")
    private String exchangeDlx;

    @Value("${rabbitmq.order.update-status.queue-dlq}")
    private String queueDlq;

    @Bean
    public FanoutExchange statusUpdatedExchange() {
        return ExchangeBuilder.fanoutExchange(exchange).durable(false).autoDelete().build();
    }

    @Bean //Já existe um bean do tipo Queue, por isso que eu 
    public Queue statusUpdatedQueue() {
        return QueueBuilder.nonDurable(queue).deadLetterExchange(exchangeDlx).build();
    }

    // Pelo fato de ser uma fanout exchange não tem necessidade e nem como fazer o binding passando a routing key/binding key
    @Bean
    public Binding statusUpdatedBinding() {
        return BindingBuilder.bind(statusUpdatedQueue()).to(statusUpdatedExchange());
    }

    @Bean
    public FanoutExchange statusUpdatedExchangeDlx() {
        return ExchangeBuilder.fanoutExchange(exchangeDlx).durable(false).autoDelete().build();
    }

    @Bean
    public Queue statusUpdatedQueueDlq() {
        return QueueBuilder.nonDurable(queueDlq).build();
    }

    @Bean
    public Binding statusUpdatedBindingDlx() {
        return BindingBuilder.bind(statusUpdatedQueue()).to(statusUpdatedExchange());
    }
}
