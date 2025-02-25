package com.api.geolocation.presentation.services;

import com.api.geolocation.domain.Branch;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public interface IBranchService {
    void subscribe(Branch branch);
    void unsubscribe(UUID branchId);
    List<Branch> findNearestBranches(Point location);
}
