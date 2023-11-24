package com.tca.auth;

import com.tca.core.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author star.lee
 */

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.tca.*.api.feign"})
@Import(ApplicationConfiguration.class)
@EnableAsync
public class TcaAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcaAuthApplication.class, args);
    }

}
