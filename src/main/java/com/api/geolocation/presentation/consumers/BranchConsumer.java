package com.api.geolocation.presentation.consumers;

import com.api.geolocation.application.transaction.BranchDTO;
import com.api.geolocation.application.services.IBranchService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstantHandler.QUEUE_COORDINATES;

@Service
@AllArgsConstructor
public class CoordinatesConsumer {

    private IBranchService branchService;

    @RabbitListener(queues = QUEUE_COORDINATES)
    public void subscribeBranchCoordinate(BranchDTO branchDto) {
        branchService.subscribe(branchDto);
    }

    public void unsubscribeBranchCoordinate(BranchDTO branchDto) {
        branchService.unsubscribe(branchDto);
    }
}

