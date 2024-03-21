package com.tca.utils.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * @author star.lee
 */
@Component
public class HikariConfiguration implements ApplicationRunner {

    private final HikariDataSource hikariDataSource;

    public HikariConfiguration(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    @Autowired
    public void run(ApplicationArguments args) throws SQLException {
        hikariDataSource.getConnection();
    }
}