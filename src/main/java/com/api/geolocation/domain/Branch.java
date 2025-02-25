package com.api.geolocation.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Branch {
    UUID id;
    Float[] coordinates;
    boolean active;
}
