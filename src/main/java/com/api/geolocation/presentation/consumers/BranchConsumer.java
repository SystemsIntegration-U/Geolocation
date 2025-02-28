package com.api.geolocation.presentation.consumers;

import com.api.geolocation.application.services.IBranchService;
import com.api.geolocation.application.transaction.BranchDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCH_SUBSCRIPTION_QUEUE;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCH_UNSUBSCRIPTION_QUEUE;

@Component
@AllArgsConstructor
@Slf4j
public class BranchConsumer {

    private IBranchService branchService;

    @RabbitListener(queues = BRANCH_SUBSCRIPTION_QUEUE)
    public void subscribeBranchCoordinate(BranchDTO branchDto) {
        try {
            branchService.subscribe(branchDto);
            log.info("subscribe: ({}, {})", branchDto.getLocation().getLatitude(), branchDto.getLocation().getLongitude());
        } catch (Exception exception) {
            log.warn(exception.getMessage());
        }
    }

    @RabbitListener(queues = BRANCH_UNSUBSCRIPTION_QUEUE)
    public void unsubscribeBranchCoordinate(BranchDTO branchDto) {
        try {
            branchService.unsubscribe(branchDto);
            log.info("unsubscribe: ({}, {})", branchDto.getLocation().getLatitude(), branchDto.getLocation().getLongitude());
        } catch (Exception exception) {
            log.warn(exception.getMessage());
        }
    }
}

