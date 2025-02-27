package com.api.geolocation.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Entity
@Data
@Table(name = "branch")
public class Branch {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(columnDefinition = "geography(Point,4326)", nullable = false)
    private Point coordinates;

    private boolean active;
}