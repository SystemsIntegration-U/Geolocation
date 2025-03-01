package com.api.geolocation.application.services;

import com.api.geolocation.application.transaction.BranchDTO;
import com.api.geolocation.domain.entities.Branch;
import com.api.geolocation.domain.repository.IBranchRepository;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
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
    public List<BranchDTO> findNearestBranches(double latitude, double longitude, double range) {
        List<Branch> branches = branchRepository.findNearestBranchesWithinRange(latitude, longitude, range);

        return branches.stream()
                .map(branch -> modelMapper.map(branch, BranchDTO.class))
                .toList();
    }
}