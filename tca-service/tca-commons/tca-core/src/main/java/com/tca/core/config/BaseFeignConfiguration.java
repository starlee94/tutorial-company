package com.tca.core.config;

import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

final class BaseFeignConfiguration {
    @Bean
    public ConfigProperties globalConfigProperties(){
        ConfigProperties configProperties = new ConfigProperties();
        List<String> excludeUris = new ArrayList<>(9);
        excludeUris.add("/actuator");
        excludeUris.add("/actuator/health");
        excludeUris.add("/actuator/info");
        excludeUris.add("/druid");
        excludeUris.add("/auth");

        configProperties.setAllowOrigin("*");
        configProperties.setAllowMethods("*");
        configProperties.setAllowHeaders("*");
        configProperties.setLogFilterExcludeUris(excludeUris);
        return configProperties;
    }
}
