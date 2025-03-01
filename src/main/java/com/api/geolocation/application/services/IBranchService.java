package com.api.geolocation.application.services;

import com.api.geolocation.application.transaction.BranchDTO;
import java.util.List;

public interface IBranchService {
    void subscribe(BranchDTO branch);
    void unsubscribe(BranchDTO branch);
    List<BranchDTO> findNearestBranches(double latitude, double longitude, double range);
}
