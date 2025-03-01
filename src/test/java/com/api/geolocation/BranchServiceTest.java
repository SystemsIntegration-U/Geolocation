package com.api.geolocation;

import com.api.geolocation.application.services.BranchService;
import com.api.geolocation.application.transaction.BranchDTO;
import com.api.geolocation.application.transaction.LocationDTO;
import com.api.geolocation.domain.entities.Branch;
import com.api.geolocation.domain.repository.IBranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {
    @Mock
    private IBranchRepository branchRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BranchService branchService;

    private BranchDTO activeBranchDTO;
    private Branch activeBranch;
    private Branch inactiveBranch;
    private Point mockPoint;

    @BeforeEach
    void setUp() {
        activeBranchDTO = new BranchDTO(new LocationDTO(-99.1332, 19.4326));

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
}