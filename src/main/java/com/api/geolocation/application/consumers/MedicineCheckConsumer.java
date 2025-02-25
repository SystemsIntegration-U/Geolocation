package com.api.geolocation.application.consumers;

import com.api.geolocation.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MedicineCheckConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_MEDICINE_CHECK)
    public boolean checkMedicine(String medicineId) {
        System.out.println("Checking medicine ID: " + medicineId);

        boolean isAvailable = medicineId.hashCode() % 2 == 0;
        System.out.println("Medicine availability: " + isAvailable);
        return isAvailable;
    }
}
