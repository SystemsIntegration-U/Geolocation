package com.api.geolocation.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.Point;
import java.util.UUID;

@Entity
@Data
@Table(name = "Branch")
public class Branch {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(columnDefinition = "geography(Point,4326)", nullable = false)
    private Point coordinates;

    private boolean active;
}