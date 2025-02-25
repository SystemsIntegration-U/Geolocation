package com.api.geolocation.infrastructure.repository;

import com.api.geolocation.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IBranchRepository extends JpaRepository<Branch, UUID> {
}
