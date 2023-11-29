package com.tca.core.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author star.lee
 */

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class HPConfiguration  extends HikariConfig {
    @Bean
    public DataSource dataSource() throws SQLException {
        return new HikariDataSource(this);
    }

}