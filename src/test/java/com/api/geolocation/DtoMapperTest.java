package com.api.geolocation;

import com.api.geolocation.application.transaction.BranchDTO;
import com.api.geolocation.application.transaction.LocationDTO;
import com.api.geolocation.domain.entities.Branch;
import com.api.geolocation.infrastructure.mapper.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;

class DtoMapperTest {

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        GeometryFactory geometryFactory = new GeometryFactory();
        DtoMapper dtoMapper = new DtoMapper(geometryFactory);
        modelMapper = dtoMapper.modelMapper();
    }

    @Test
    void testLocationDtoToPointMapping() {
        LocationDTO locationDTO = new LocationDTO(10.0, 20.0);

        Point point = modelMapper.map(locationDTO, Point.class);

        assertThat(point).isNotNull();
        assertThat(point.getX()).isEqualTo(20.0);
        assertThat(point.getY()).isEqualTo(10.0);
    }

    @Test
    void testPointToLocationDtoMapping() {
        Point point = new GeometryFactory().createPoint(new org.locationtech.jts.geom.Coordinate(20.0, 10.0));

        LocationDTO locationDTO = modelMapper.map(point, LocationDTO.class);

        assertThat(locationDTO).isNotNull();
        assertThat(locationDTO.getLatitude()).isEqualTo(10.0);
        assertThat(locationDTO.getLongitude()).isEqualTo(20.0);
    }

    @Test
    void testBranchDtoToBranchMapping() {
        LocationDTO locationDTO = new LocationDTO(10.0, 20.0);
        BranchDTO branchDTO = new BranchDTO(locationDTO);

        Branch branch = modelMapper.map(branchDTO, Branch.class);

        assertThat(branch).isNotNull();
        assertThat(branch.getCoordinates()).isNotNull();
        assertThat(branch.getCoordinates().getX()).isEqualTo(20.0);
        assertThat(branch.getCoordinates().getY()).isEqualTo(10.0);
        assertThat(branch.isActive()).isFalse();
    }

    @Test
    void testBranchToBranchDtoMapping() {
        Point point = new GeometryFactory().createPoint(new org.locationtech.jts.geom.Coordinate(20.0, 10.0));
        Branch branch = new Branch();
        branch.setCoordinates(point);

        BranchDTO branchDTO = modelMapper.map(branch, BranchDTO.class);

        assertThat(branchDTO).isNotNull();
        assertThat(branchDTO.getLocation()).isNotNull();
        assertThat(branchDTO.getLocation().getLatitude()).isEqualTo(10.0);
        assertThat(branchDTO.getLocation().getLongitude()).isEqualTo(20.0);
    }
}
