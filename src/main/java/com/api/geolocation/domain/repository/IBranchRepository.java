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
    @Query(value = """
        SELECT * 
        FROM branch 
        WHERE active = true 
          AND ST_DWithin(
              coordinates, 
              ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography, 
              :range
          )
        ORDER BY ST_Distance(
            coordinates, 
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography
        ) ASC
        """, nativeQuery = true)
    List<Branch> findNearestBranchesWithinRange(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("range") double range
    );

    Optional<Branch> findByCoordinates(Point coordinates);
}
