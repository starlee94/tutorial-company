package com.tca.utils.config;

import com.tca.utils.config.feign.ActiveFeignClientConfiguration;
import com.tca.utils.constant.finals.SpringBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;


/**
 * @author star.lee
 */

@Configuration
@Import({ActiveFeignClientConfiguration.class, HikariConfiguration.class})
@Slf4j
public class ApplicationConfiguration {

    @Value("${mybatis_dao_scan_packages}")
    private String mybatisDaoScanPackages;

    @PostConstruct
    private void init() throws Exception {
        DaoCache.init(mybatisDaoScanPackages);
    }

    @Bean
    public SpringBeanFactory springBeanFactory() {
        return new SpringBeanFactory();
    }
}
