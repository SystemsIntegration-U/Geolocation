package com.api.geolocation.application.consumers;

import com.api.geolocation.config.RabbitMQConfig;
import com.api.geolocation.domain.Branch;
import com.api.geolocation.presentation.services.IBranchService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class CoordinatesConsumer {
    private final IBranchService branchService;

    public CoordinatesConsumer(IBranchService branchService) {
        this.branchService = branchService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_COORDINATES)
    public void processCoordinates(String message) {
        System.out.println("Received coordinates: " + message);

        String[] data = message.split(",");
        int latitude = Integer.parseInt(data[0]);
        int longitude = Integer.parseInt(data[1]);

        Point location = new Point(latitude, longitude);
        Branch branch = new Branch();
        branch.setCoordinates(location);

        branchService.subscribe(branch);
        System.out.println("Branch subscribed with coordinates: " + location);
    }
}

