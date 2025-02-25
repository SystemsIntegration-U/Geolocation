package com.api.geolocation.presentation.services;

import com.api.geolocation.domain.Branch;
import com.api.geolocation.infrastructure.repository.IBranchRepository;

import java.util.Optional;

public class BranchService implements IBranchService {
    private IBranchRepository branchRepository;

    @Override
    public void subscribe(Branch branch) {
        branchRepository.save(branch);
    }

    @Override
    public void unsubscribe(Branch branch) {
        branchRepository.findById(branch.getId()).ifPresent(value -> value.setActive(false));
    }
}
