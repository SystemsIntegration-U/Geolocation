package com.api.geolocation.presentation.services;

import com.api.geolocation.domain.Branch;

public interface IBranchService {
    void subscribe(Branch branch);
    void unsubscribe(Branch branch);
}
