package com.api.geolocation.application.transaction;

import lombok.Value;

import java.awt.*;
import java.util.UUID;

@Value
public class BranchDTO {
    UUID branchId;
    Point location;
}
