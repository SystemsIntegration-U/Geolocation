package com.api.geolocation.application.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;


@Value
@Getter
@Setter
@AllArgsConstructor
public class BranchDTO {
    LocationDTO location;
}
