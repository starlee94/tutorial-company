package com.tca.core.config.interceptor;

import com.tca.core.config.holder.RequestHolder;
import com.tca.core.constant.interfaces.ProjectConfigConstant;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author star.lee
 */
public class FeignReqInterceptor implements RequestInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String HEADER_TRACE_ID = "trace";

    public FeignReqInterceptor() {
        logger.info("----------------------------------- {} initialization completed! -----------------------------------", this.getClass().getSimpleName());
    }

    @Override
    public void apply(RequestTemplate template) {
        String url = template.url();
        logger.info("Accepting Feign API: {}", url);
        HttpServletRequest httpServletRequest = RequestHolder.getCurrenctRequest();
        if (httpServletRequest == null){
            logger.warn("Empty httpServletRequest!");
            return;
        }
        String token = httpServletRequest.getHeader(ProjectConfigConstant.TOKEN_HEADER_NAME);
        String requestSource = httpServletRequest.getHeader(ProjectConfigConstant.REQUEST_SOURCE);
        Request request = template.request();
        template.header(ProjectConfigConstant.TOKEN_HEADER_NAME, token);
        String path = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();
        template.header("ReqURI", path);
        template.header("ReqMethod", method);
        template.header(ProjectConfigConstant.REQUEST_SOURCE, requestSource);
        logger.info("Feign-Method: {}，Current token info: {}", request.method(), token);
    }
}
