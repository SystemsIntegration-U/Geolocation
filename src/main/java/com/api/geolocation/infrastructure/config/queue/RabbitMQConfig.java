package com.api.geolocation.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.api.geolocation.infrastructure.config.RabbitMQConstantHandler.COORDINATES_KEY;
import static com.api.geolocation.infrastructure.config.RabbitMQConstantHandler.MEDICINE_KEY;
import static com.api.geolocation.infrastructure.config.RabbitMQConstantHandler.QUEUE_COORDINATES;
import static com.api.geolocation.infrastructure.config.RabbitMQConstantHandler.QUEUE_MEDICINE_CHECK;
import static com.api.geolocation.infrastructure.config.RabbitMQConstantHandler.TOPIC_EXCHANGE;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue coordinatesQueue() {
        return new Queue(QUEUE_COORDINATES, false);
    }

    @Bean
    public Queue medicineCheckQueue() {
        return new Queue(QUEUE_MEDICINE_CHECK, false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding bindingCoordinates(Queue coordinatesQueue, DirectExchange exchange) {
        return BindingBuilder.bind(coordinatesQueue).to(exchange).with(COORDINATES_KEY);
    }

    @Bean
    public Binding bindingMedicineCheck(Queue medicineCheckQueue, DirectExchange exchange) {
        return BindingBuilder.bind(medicineCheckQueue).to(exchange).with(MEDICINE_KEY);
    }
}
