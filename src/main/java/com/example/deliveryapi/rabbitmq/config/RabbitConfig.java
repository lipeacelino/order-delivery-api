package com.example.deliveryapi.rabbitmq.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public MessageConverter jsonMessageConverter() { //testar sem isso pra ver se funciona
        return new Jackson2JsonMessageConverter();
    }

    //Cria as exchanges e filas assim que iniciar a aplicação
    @Bean
    public ApplicationRunner initializeConnection(ConnectionFactory connectionFactory) {
        return args -> connectionFactory.createConnection().close();
    }
}
