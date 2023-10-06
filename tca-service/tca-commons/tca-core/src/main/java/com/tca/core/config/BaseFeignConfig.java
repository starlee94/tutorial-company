package com.tca.core.config;

import com.tca.core.enums.SerializerFeature;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

final class BaseFeignConfig {
    @Bean
    public ConfigProperties globalConfigProperties(){
        ConfigProperties configProperties = new ConfigProperties();
        List<String> excludeUris = new ArrayList<>(9);
        excludeUris.add("/actuator");
        excludeUris.add("/actuator/health");
        excludeUris.add("/actuator/info");
        excludeUris.add("/druid");

        List<SerializerFeature> serializerFeatures = new ArrayList<>(12);
        // 为null的list输出为 []
        serializerFeatures.add(SerializerFeature.WriteNullListAsEmpty);
        // 输出值为null的字段
        serializerFeatures.add(SerializerFeature.WriteMapNullValue);
        // Date输出为 JSON.DEFFAULT_DATE_FORMAT 格式，JSON.DEFFAULT_DATE_FORMAT可以在项目初始化时指定
        serializerFeatures.add(SerializerFeature.WriteDateUseDateFormat);
        // 消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
        serializerFeatures.add(SerializerFeature.DisableCircularReferenceDetect);

        configProperties.setAllowOrigin("*");
        configProperties.setAllowMethods("*");
        configProperties.setAllowHeaders("*");
        configProperties.setLogFilterExcludeUris(excludeUris);
        configProperties.setSerializerFeatures(serializerFeatures);
        return configProperties;
    }
}
