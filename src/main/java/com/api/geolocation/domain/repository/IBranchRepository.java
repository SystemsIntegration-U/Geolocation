package com.api.geolocation.domain.repository;

import com.api.geolocation.domain.entities.Branch;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IBranchRepository extends JpaRepository<Branch, UUID> {
    @Query("SELECT b FROM Branch b WHERE b.active = true ORDER BY ST_DistanceSphere(b.coordinates, :location) ASC")
    List<Branch> findNearestBranches(@Param("location") Point location);

    Optional<Branch> findByCoordinates(Point coordinates);
}
