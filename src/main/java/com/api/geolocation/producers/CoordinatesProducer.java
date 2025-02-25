package com.api.geolocation.producers;

import com.api.geolocation.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordinatesProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String sendCoordinates(double latitude, double longitude, String medicineId) {
        String message = latitude + "," + longitude + "," + medicineId;
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "route.coordinates", message);
        return "Coordinates sent: " + message;
    }
}
