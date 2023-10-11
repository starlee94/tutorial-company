package com.tca.core.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author star.lee
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "Company Portal API", version = "v1"))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SpringDocConfig {

//    @Bean
//    public GroupedOpenApi apiVer1(){
//        return buildApi("v1");
//    }
//
//    @Bean
//    public GroupedOpenApi apiVer2(){
//        return buildApi("v2");
//    }
//
//    private GroupedOpenApi buildApi(String version){
//        return GroupedOpenApi.builder()
//                .addOpenApiCustomiser(openApi -> openApi.getInfo().setVersion(version))
//                .group(String.format("API-%s", version))
//                .pathsToMatch(String.format("/api/%s/**", version),"/api/v0/**")
//                .displayName(String.format("API %s", version))
//                .build();
//    }
}
