package com.tca.core.config.feign;

import com.tca.core.config.decoder.CustomDecoder;
import com.tca.core.config.decoder.CustomErrorDecoder;
import com.tca.core.config.interceptor.FeignReqInterceptor;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;

/**
 * @author star.lee
 */
@AutoConfigureBefore(FeignClientConfiguration.class)
public class ActiveFeignClientConfiguration {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;


    @Bean
    @ConditionalOnMissingBean(CustomDecoder.class)
    public CustomDecoder feignDecoder() {
        return new CustomDecoder(new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters))));
    }

    @Bean
    @ConditionalOnMissingBean(CustomErrorDecoder.class)
    public CustomErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }


    /**
     * feign请求拦截器，用于传递请求参数
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(FeignReqInterceptor.class)
    public FeignReqInterceptor feignReqInterceptor(){
        return new FeignReqInterceptor();
    }
}
