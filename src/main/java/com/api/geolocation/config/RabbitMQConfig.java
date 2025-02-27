package com.api.geolocation.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_COORDINATES = "coordinatesQueue";
    public static final String QUEUE_MEDICINE_CHECK = "medicineCheckQueue";
    public static final String EXCHANGE = "EXCHANGE";

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
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingCoordinates(Queue coordinatesQueue, DirectExchange exchange) {
        return BindingBuilder.bind(coordinatesQueue).to(exchange).with("route.coordinates");
    }

    @Bean
    public Binding bindingMedicineCheck(Queue medicineCheckQueue, DirectExchange exchange) {
        return BindingBuilder.bind(medicineCheckQueue).to(exchange).with("route.medicineCheck");
    }
}
