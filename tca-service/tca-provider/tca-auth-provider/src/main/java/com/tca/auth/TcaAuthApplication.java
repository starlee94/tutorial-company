package com.tca.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.tca.*.api.feign"})
@ComponentScan(value = {"com.tca"})
@EnableTransactionManagement
@EnableAsync
public class TcaAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcaAuthApplication.class, args);
    }

}
