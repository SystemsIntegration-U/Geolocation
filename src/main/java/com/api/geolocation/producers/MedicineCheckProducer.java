package com.api.geolocation.producers;

import com.api.geolocation.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicineCheckProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String checkMedicineAvailability(String medicineId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "route.medicineCheck", medicineId);
        return "Medicine check request sent for: " + medicineId;
    }
}
