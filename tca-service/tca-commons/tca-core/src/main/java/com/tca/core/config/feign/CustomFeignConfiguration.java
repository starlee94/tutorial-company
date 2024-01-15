package com.tca.core.config.feign;

import com.tca.core.config.interceptor.FeignReqInterceptor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;

@AutoConfigureBefore(FeignClientConfiguration.class)
public class CustomFeignConfiguration {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    /**
     * feign请求拦截器，用于传递请求参数
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(FeignReqInterceptor.class)
    public FeignReqInterceptor feignReqInterceptor(){
        FeignReqInterceptor feignReqInterceptor = new FeignReqInterceptor();
        return feignReqInterceptor;
    }

//
//    @Bean
//    public SqlDataDecoder sqlDataDecoder(){
//        return new SqlDataDecoder(new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters))));
//    }
}
