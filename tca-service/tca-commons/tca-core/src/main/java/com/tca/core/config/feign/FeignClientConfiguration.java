package com.tca.core.config.feign;

import com.netflix.hystrix.HystrixCommand;
import com.tca.core.config.interceptor.FeignReqInterceptor;
import feign.Contract;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hystrix.HystrixFeign;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.DefaultFeignLoggerFactory;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author star.lee
 */
@Configuration
public class FeignClientConfiguration {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;
    @Autowired(
            required = false
    )
    private List<AnnotatedParameterProcessor> parameterProcessors = new ArrayList();
    @Autowired(
            required = false
    )
    private List<FeignFormatterRegistrar> feignFormatterRegistrars = new ArrayList();
    @Autowired(
            required = false
    )
    private Logger logger;

    public FeignClientConfiguration() {}

    @Bean
    @ConditionalOnMissingBean
    public Decoder feignDecoder() {
        return new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters)));
    }

    @Bean
    @ConditionalOnMissingBean
    public Encoder feignEncoder() {
        return new SpringEncoder(this.messageConverters);
    }

    @Bean
    @ConditionalOnMissingBean
    public Contract feignContract(ConversionService feignConversionService) {
        return new SpringMvcContract(this.parameterProcessors, feignConversionService);
    }

    @Bean
    public FormattingConversionService feignConversionService() {
        FormattingConversionService conversionService = new DefaultFormattingConversionService();
        Iterator var2 = this.feignFormatterRegistrars.iterator();

        while(var2.hasNext()) {
            FeignFormatterRegistrar feignFormatterRegistrar = (FeignFormatterRegistrar)var2.next();
            feignFormatterRegistrar.registerFormatters(conversionService);
        }

        return conversionService;
    }

    @Bean
    @ConditionalOnMissingBean
    public Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public Feign.Builder feignBuilder(Retryer retryer) {
        return Feign.builder().retryer(retryer);
    }

    @Bean
    @ConditionalOnMissingBean({FeignLoggerFactory.class})
    public FeignLoggerFactory feignLoggerFactory() {
        return new DefaultFeignLoggerFactory(this.logger);
    }

    @Configuration
    @ConditionalOnClass({HystrixCommand.class, HystrixFeign.class})
    protected static class HystrixFeignConfiguration {
        protected HystrixFeignConfiguration() {
        }

        @Bean
        @Scope("prototype")
        @ConditionalOnMissingBean
        @ConditionalOnProperty(
                name = {"feign.hystrix.enabled"}
        )
        public Feign.Builder feignHystrixBuilder() {
            return HystrixFeign.builder();
        }
    }
    @Bean
    @ConditionalOnMissingBean(FeignReqInterceptor.class)
    public FeignReqInterceptor feignReqInterceptor(){
        return new FeignReqInterceptor();
    }


}