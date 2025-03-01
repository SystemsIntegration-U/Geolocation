package com.api.geolocation;

import com.api.geolocation.application.services.DistanceCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistanceCalculatorTest {
    @Test
    public void testDistance() {
        double lat1 = 40.7128;
        double lon1 = -74.0060;
        double lat2 = 34.0522;
        double lon2 = -118.2437;

        double distance = DistanceCalculator.calculateDistance(lat1, lon1, lat2, lon2);
        assertEquals(3935.746254609722, distance);
    }
}
