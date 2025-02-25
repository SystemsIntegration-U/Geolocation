package com.api.geolocation.presentation.services;

import com.api.geolocation.domain.Branch;
import com.api.geolocation.infrastructure.repository.IBranchRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BranchService implements IBranchService {
    private IBranchRepository branchRepository;

    @Override
    public void subscribe(Branch branch) {
        branchRepository.save(branch);
    }

    @Override
    public void unsubscribe(UUID branchId) {
        branchRepository.findById(branchId).ifPresent(value -> value.setActive(false));
    }

    @Override
    public List<Branch> findNearestBranches(Point location) {
        return branchRepository.findNearestBranches(location);
    }
}
