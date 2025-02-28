package com.api.geolocation.presentation.consumers;

import com.api.geolocation.application.services.IBranchService;
import com.api.geolocation.application.transaction.BranchDTO;
import com.api.geolocation.application.transaction.RequiredMedicineDTO;
import com.api.geolocation.presentation.producers.LocationProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.MEDICINE_SEARCH_QUEUE;

@Component
@AllArgsConstructor
@Slf4j
public class LocationConsumer {

    private IBranchService branchService;
    private LocationProducer locationProducer;

    @RabbitListener(queues = MEDICINE_SEARCH_QUEUE)
    public void searchForMedicine(RequiredMedicineDTO requiredMedicineDTO) {
        try {
            List<BranchDTO> nearestBranches = branchService.findNearestBranches(
                    requiredMedicineDTO.getOriginCoordinates().getLatitude(),
                    requiredMedicineDTO.getOriginCoordinates().getLongitude()
            );
            locationProducer.sendBranchesWithMedicine(nearestBranches, requiredMedicineDTO);
            log.info("searchForMedicine: ({}, {})", requiredMedicineDTO.getOriginCoordinates().getLatitude(), requiredMedicineDTO.getOriginCoordinates().getLongitude());
            log.info("searchForMedicine: ({} - stock: {})", requiredMedicineDTO.getProductDetails().getId(), requiredMedicineDTO.getProductDetails().getStock());
        } catch (Exception exception) {
            log.warn(exception.getMessage());
        }
    }
}
