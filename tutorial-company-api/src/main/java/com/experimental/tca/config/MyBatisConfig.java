package com.experimental.tca.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.experimental.tca.mapper")
public class MyBatisConfig {
}
