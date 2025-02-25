package com.api.geolocation.consumers;

import com.api.geolocation.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CoordinatesConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_COORDINATES)
    public String processCoordinates(String message) {
        System.out.println("Received coordinates: " + message);

        String[] data = message.split(",");
        double newLatitude = Double.parseDouble(data[0]) + 0.001;
        double newLongitude = Double.parseDouble(data[1]) + 0.001;

        String response = newLatitude + "," + newLongitude;
        System.out.println("New coordinates: " + response);
        return response;
    }
}
