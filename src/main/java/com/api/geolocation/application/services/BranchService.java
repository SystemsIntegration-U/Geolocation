package com.api.geolocation.application.services;

import com.api.geolocation.application.transaction.BranchDTO;
import com.api.geolocation.domain.entities.Branch;
import com.api.geolocation.domain.repository.IBranchRepository;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BranchService implements IBranchService {
    private final IBranchRepository branchRepository;
    private final GeometryFactory geometryFactory;
    private final ModelMapper modelMapper;

    @Override
    public void subscribe(BranchDTO branchDto) {
        Branch branch = modelMapper.map(branchDto, Branch.class);
        branch.setActive(true);
        branchRepository.save(branch);
    }

    @Override
    public void unsubscribe(BranchDTO branchDto) {
        Branch branch = modelMapper.map(branchDto, Branch.class);
        branchRepository.findByCoordinates(branch.getCoordinates())
                .ifPresent(branchSaved -> {
                    branch.setActive(false);
                    branchRepository.save(branch);
                });
    }

    @Override
    public List<BranchDTO> findNearestBranches(double latitude, double longitude) {
        Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        List<Branch> branches = branchRepository.findNearestBranches(location);

        return branches.subList(0, Math.min(3, branches.size()))
                .stream()
                .map(branch -> modelMapper.map(branch, BranchDTO.class))
                .toList();
    }
}