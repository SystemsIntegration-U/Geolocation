package com.api.geolocation;

import com.api.geolocation.application.services.BranchService;
import com.api.geolocation.application.transaction.BranchDTO;
import com.api.geolocation.application.transaction.LocationDTO;
import com.api.geolocation.domain.entities.Branch;
import com.api.geolocation.domain.repository.IBranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {

    @Mock
    private IBranchRepository branchRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private GeometryFactory geometryFactory;

    @InjectMocks
    private BranchService branchService;

    private BranchDTO activeBranchDTO;
    private BranchDTO inactiveBranchDTO;
    private Branch activeBranch;
    private Branch inactiveBranch;
    private Point mockPoint;

    @BeforeEach
    void setUp() {
        activeBranchDTO = new BranchDTO(new LocationDTO(-99.1332, 19.4326));
        inactiveBranchDTO = new BranchDTO(new LocationDTO(-58.3816, -34.6037));

        mockPoint = mock(Point.class);

        activeBranch = new Branch();
        activeBranch.setCoordinates(mockPoint);
        activeBranch.setActive(true);

        inactiveBranch = new Branch();
        inactiveBranch.setCoordinates(mockPoint);
        inactiveBranch.setActive(false);
    }

    @Test
    void testSubscribe() {
        when(modelMapper.map(activeBranchDTO, Branch.class)).thenReturn(activeBranch);
        when(branchRepository.save(any(Branch.class))).thenReturn(activeBranch);

        branchService.subscribe(activeBranchDTO);

        verify(modelMapper, times(1)).map(activeBranchDTO, Branch.class);
        verify(branchRepository, times(1)).save(any(Branch.class));
    }

    @Test
    void testUnsubscribe() {
        when(modelMapper.map(activeBranchDTO, Branch.class)).thenReturn(activeBranch);
        when(branchRepository.findByCoordinates(any(Point.class))).thenReturn(Optional.of(activeBranch));

        branchService.unsubscribe(activeBranchDTO);

        verify(modelMapper, times(1)).map(activeBranchDTO, Branch.class);
        verify(branchRepository, times(1)).findByCoordinates(any(Point.class));
        verify(branchRepository, times(1)).save(any(Branch.class));
    }

    @Test
    void testFindNearestBranches() {
        List<Branch> branchEntities = Arrays.asList(activeBranch, inactiveBranch);
        List<BranchDTO> expectedDTOs = Arrays.asList(activeBranchDTO, inactiveBranchDTO);

        when(geometryFactory.createPoint(any(Coordinate.class))).thenReturn(mockPoint);
        when(branchRepository.findNearestBranches(any(Point.class))).thenReturn(branchEntities);
        when(modelMapper.map(activeBranch, BranchDTO.class)).thenReturn(activeBranchDTO);
        when(modelMapper.map(inactiveBranch, BranchDTO.class)).thenReturn(inactiveBranchDTO);

        List<BranchDTO> nearestBranches = branchService.findNearestBranches(19.4326, -99.1332);

        assertNotNull(nearestBranches);
        assertEquals(2, nearestBranches.size());
        assertTrue(nearestBranches.contains(activeBranchDTO));

        verify(geometryFactory, times(1)).createPoint(any(Coordinate.class));
        verify(branchRepository, times(1)).findNearestBranches(any(Point.class));
        verify(modelMapper, times(2)).map(any(Branch.class), eq(BranchDTO.class));
    }

    @Test
    void testFindNearestBranches_EmptyResult() {
        when(geometryFactory.createPoint(any(Coordinate.class))).thenReturn(mockPoint);
        when(branchRepository.findNearestBranches(any(Point.class))).thenReturn(List.of());

        List<BranchDTO> nearestBranches = branchService.findNearestBranches(19.4326, -99.1332);

        assertNotNull(nearestBranches);
        assertTrue(nearestBranches.isEmpty());

        verify(geometryFactory, times(1)).createPoint(any(Coordinate.class));
        verify(branchRepository, times(1)).findNearestBranches(any(Point.class));
        verify(modelMapper, never()).map(any(Branch.class), eq(BranchDTO.class));
    }
}