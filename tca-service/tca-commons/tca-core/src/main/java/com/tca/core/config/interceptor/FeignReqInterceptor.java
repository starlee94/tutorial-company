package com.tca.core.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author star.lee
 */
@Component
public class FeignReqInterceptor implements RequestInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private static final String HEADER_TRACE_ID = "trace";

    public FeignReqInterceptor() {
        logger.info("----------------------------------- {} initialization completed! -----------------------------------", this.getClass().getSimpleName());
    }

    @Override
    public void apply(RequestTemplate template) {
        String url = template.url();
        logger.info("Accepting Feign API: {}", url);
    }
}
