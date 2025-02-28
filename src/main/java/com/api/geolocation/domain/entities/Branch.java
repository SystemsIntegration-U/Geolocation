package com.api.geolocation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "branch")
public class Branch {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(columnDefinition = "geography(Point,4326)", nullable = false)
    private Point coordinates;

    private boolean active;
}