package com.api.geolocation.presentation.services;

import com.api.geolocation.domain.Branch;
import com.api.geolocation.infrastructure.repository.IBranchRepository;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BranchService implements IBranchService {
    private final IBranchRepository branchRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public BranchService(IBranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public void subscribe(Branch branch) {
        branchRepository.save(branch);
    }

    @Override
    public void unsubscribe(UUID branchId) {
        branchRepository.findById(branchId).ifPresent(branch -> {
            branch.setActive(false);
            branchRepository.save(branch);
        });
    }

    @Override
    public List<Branch> findNearestBranches(double latitude, double longitude) {
        Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        List<Branch> branches = branchRepository.findNearestBranches(location);

        return branches.subList(0, Math.min(3, branches.size()));
    }
}