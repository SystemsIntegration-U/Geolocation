package com.api.geolocation.presentation.producers;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstantHandler.TOPIC_EXCHANGE;

@Service
@AllArgsConstructor
public class CoordinatesProducer {

    private RabbitTemplate rabbitTemplate;

    public String sendCoordinates(double latitude, double longitude, String medicineId) {
        String message = latitude + "," + longitude + "," + medicineId;
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, "route.coordinates", message);
        return "Coordinates sent: " + message;
    }
}
