package com.api.geolocation.infrastructure.repository;

import com.api.geolocation.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.UUID;

@Repository
public interface IBranchRepository extends JpaRepository<Branch, UUID> {
    @Query("SELECT b FROM Branch b WHERE b.active = true ORDER BY ST_Distance(b.coordinates, :location) ASC LIMIT 3")
    List<Branch> findNearestBranches(@Param("location") Point location);
}
