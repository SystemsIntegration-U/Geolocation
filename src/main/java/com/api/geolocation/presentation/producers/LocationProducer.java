package com.api.geolocation.presentation.producers;

import com.api.geolocation.application.transaction.BranchDTO;
import com.api.geolocation.application.transaction.BranchesWithMedicineDTO;
import com.api.geolocation.application.transaction.LocationDTO;
import com.api.geolocation.application.transaction.RequiredMedicineDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCHES_WITH_MEDICINE_KEY;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.MEDICINE_EXCHANGE;

@Component
@AllArgsConstructor
@Slf4j
public class LocationProducer {

    private RabbitTemplate rabbitTemplate;

    public void sendBranchesWithMedicine(List<BranchDTO> nearestBranches, RequiredMedicineDTO requiredMedicineDTO) {
        try {
            List<LocationDTO> nearbyPoints = nearestBranches.stream()
                    .map(BranchDTO::getLocation)
                    .collect(Collectors.toList());

            BranchesWithMedicineDTO branchesWithMedicineDTO = new BranchesWithMedicineDTO(
                    requiredMedicineDTO.getOriginCoordinates(),
                    nearbyPoints,
                    requiredMedicineDTO.getProductDetails(),
                    requiredMedicineDTO.getRange()
            );

            rabbitTemplate.convertAndSend(
                    MEDICINE_EXCHANGE,
                    BRANCHES_WITH_MEDICINE_KEY,
                    branchesWithMedicineDTO
            );
            log.info("sendBranchesWithMedicine: (arrayLength: {}, medicine: {}, medicineStock: {})",
                    nearestBranches.size(), requiredMedicineDTO.getProductDetails().getId(), requiredMedicineDTO.getProductDetails().getStock());
        } catch (Exception exception) {
            log.warn(exception.getMessage());
        }
    }
}