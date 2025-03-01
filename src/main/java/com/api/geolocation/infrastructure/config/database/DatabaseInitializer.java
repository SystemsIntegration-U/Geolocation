package com.api.geolocation.infrastructure.config.database;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void createSpatialIndex() {
        String sql = "CREATE INDEX IF NOT EXISTS idx_branch_geography ON branch USING GIST (coordinates)";
        jdbcTemplate.execute(sql);
    }
}
