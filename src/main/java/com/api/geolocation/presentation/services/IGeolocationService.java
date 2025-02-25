package com.api.geolocation.presentation.services;

import com.api.geolocation.domain.Branch;

import java.util.List;

public interface IGeolocationService {
    List<Branch> getNearbyBranches();
}
