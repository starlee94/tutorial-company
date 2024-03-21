package com.tca.auth;

import com.tca.core.config.SecurityConfiguration;
import com.tca.utils.config.ApplicationConfiguration;
import com.tca.utils.config.ResponseTraceIdConfiguration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author star.lee
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.tca.*.api.feign"})
@Import({SecurityConfiguration.class, ApplicationConfiguration.class})
@EnableTransactionManagement
@EnableAsync
@OpenAPIDefinition(info = @Info(title = "Auth API", version = "v1"))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@ComponentScan(value = {"com.tca"}, basePackageClasses = {ResponseTraceIdConfiguration.class})
public class TcaAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcaAuthApplication.class, args);
    }

}
