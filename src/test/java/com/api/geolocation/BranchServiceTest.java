package com.api.geolocation;

import com.api.geolocation.domain.Branch;
import com.api.geolocation.infrastructure.repository.IBranchRepository;
import com.api.geolocation.presentation.services.BranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {

    @Mock
    private IBranchRepository branchRepository;

    @InjectMocks
    private BranchService branchService;

    private GeometryFactory geometryFactory;
    private Branch activeBranch;
    private Branch inactiveBranch;

    @BeforeEach
    void setUp() {
        geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        activeBranch = new Branch();
        activeBranch.setId(UUID.randomUUID());
        activeBranch.setCoordinates(geometryFactory.createPoint(new Coordinate(-99.1332, 19.4326)));
        activeBranch.setActive(true);

        inactiveBranch = new Branch();
        inactiveBranch.setId(UUID.randomUUID());
        inactiveBranch.setCoordinates(geometryFactory.createPoint(new Coordinate(-58.3816, -34.6037)));
        inactiveBranch.setActive(false);
    }

    @Test
    void testSubscribe() {
        when(branchRepository.save(any(Branch.class))).thenReturn(activeBranch);

        branchService.subscribe(activeBranch);

        verify(branchRepository, times(1)).save(activeBranch);
    }

    @Test
    void testUnsubscribe() {
        UUID branchId = activeBranch.getId();
        when(branchRepository.findById(branchId)).thenReturn(Optional.of(activeBranch));

        branchService.unsubscribe(branchId);

        assertFalse(activeBranch.isActive());
        verify(branchRepository, times(1)).save(activeBranch);
    }

    @Test
    void testFindNearestBranches() {
        Point userLocation = geometryFactory.createPoint(new Coordinate(-99.1332, 19.4326));

        when(branchRepository.findNearestBranches(any(Point.class)))
                .thenReturn(Arrays.asList(activeBranch, inactiveBranch));

        List<Branch> nearestBranches = branchService.findNearestBranches(19.4326, -99.1332);

        assertNotNull(nearestBranches);
        assertEquals(2, nearestBranches.size());
        assertTrue(nearestBranches.contains(activeBranch));

        verify(branchRepository, times(1)).findNearestBranches(any(Point.class));
    }

    @Test
    void testFindNearestBranches_EmptyResult() {
        when(branchRepository.findNearestBranches(any(Point.class))).thenReturn(List.of());

        List<Branch> nearestBranches = branchService.findNearestBranches(19.4326, -99.1332);

        assertNotNull(nearestBranches);
        assertTrue(nearestBranches.isEmpty());

        verify(branchRepository, times(1)).findNearestBranches(any(Point.class));
    }
}
