package com.api.geolocation.infrastructure.mapper;

import com.api.geolocation.application.transaction.BranchDTO;
import com.api.geolocation.application.transaction.LocationDTO;
import com.api.geolocation.domain.entities.Branch;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.locationtech.jts.geom.Coordinate;
import org.modelmapper.Converter;

@Configuration
@AllArgsConstructor
public class DtoMapper {

    private GeometryFactory geometryFactory;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        modelMapper.getConfiguration().setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PUBLIC);
        configureCustomMappers(modelMapper);

        return modelMapper;
    }

    private void configureCustomMappers(ModelMapper modelMapper) {
        configureLocationMapper(modelMapper);
        configureBranchMapper(modelMapper);
    }

    private void configureLocationMapper(ModelMapper modelMapper) {
        Converter<LocationDTO, Point> locationToPointConverter = context -> {
            LocationDTO location = context.getSource();
            if (location == null) {
                return null;
            }
            return geometryFactory.createPoint(new Coordinate(location.getLongitude(), location.getLatitude()));
        };

        Converter<Point, LocationDTO> pointToLocationDtoConverter = context -> {
            Point point = context.getSource();
            if (point == null) {
                return null;
            }
            return new LocationDTO(point.getY(), point.getX());
        };

        modelMapper.createTypeMap(LocationDTO.class, Point.class)
                .setConverter(locationToPointConverter);
        modelMapper.createTypeMap(Point.class, LocationDTO.class)
                .setConverter(pointToLocationDtoConverter);
    }

    private void configureBranchMapper(ModelMapper modelMapper) {
        Converter<BranchDTO, Branch> branchDtoToBranchConverter = context -> {
            BranchDTO branchDTO = context.getSource();
            Branch branch = new Branch();
            branch.setCoordinates(modelMapper.map(branchDTO.getLocation(), Point.class));
            branch.setActive(false);
            return branch;
        };

        Converter<Branch, BranchDTO> branchToBranchDtoConverter = context -> {
            Branch branch = context.getSource();
            if (branch == null) {
                return null;
            }

            return new BranchDTO(modelMapper.map(branch.getCoordinates(), LocationDTO.class));
        };

        modelMapper.createTypeMap(BranchDTO.class, Branch.class)
                .setConverter(branchDtoToBranchConverter);
        modelMapper.createTypeMap(Branch.class, BranchDTO.class)
                .setConverter(branchToBranchDtoConverter);
    }

}
