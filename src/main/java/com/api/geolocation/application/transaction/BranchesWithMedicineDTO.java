package com.api.geolocation.application.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.List;

@Value
@Getter
@Setter
@AllArgsConstructor
public class BranchesWithMedicineDTO {
    LocationDTO destination;
    List<LocationDTO> nearbyPoints;
    MedicineDTO medicineDTO;
    double range;
}
