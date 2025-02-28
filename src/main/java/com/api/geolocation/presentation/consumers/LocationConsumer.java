package com.api.geolocation.presentation.consumers;

import com.api.geolocation.application.services.IBranchService;
import com.api.geolocation.application.transaction.RequiredMedicineDTO;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstantHandler.QUEUE_MEDICINE_CHECK;

@Service
@AllArgsConstructor
public class MedicineConsumer {

    private IBranchService branchService;

    @RabbitListener(queues = QUEUE_MEDICINE_CHECK)
    public boolean searchForMedicine(RequiredMedicineDTO requiredMedicineDTO) {
        branchService.findNearestBranches()
    }
}
