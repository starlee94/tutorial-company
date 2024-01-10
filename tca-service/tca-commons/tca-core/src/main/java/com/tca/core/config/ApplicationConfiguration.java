package com.tca.core.config;


import com.tca.core.config.feign.ActiveFeignClientConfiguration;
import com.tca.core.constant.finals.SpringBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import javax.annotation.PostConstruct;


/**
 * @author star.lee
 */

@Configuration
@Import({ActiveFeignClientConfiguration.class, SecurityConfiguration.class, HikariConfiguration.class})
@Slf4j
public class ApplicationConfiguration {

    @Value("${mybatis_dao_scan_packages}")
    private String mybatisDaoScanPackages;

    @PostConstruct
    private void init() throws Exception {
        DaoCache.init(mybatisDaoScanPackages);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SpringBeanFactory springBeanFactory() {
        return new SpringBeanFactory();
    }

}
