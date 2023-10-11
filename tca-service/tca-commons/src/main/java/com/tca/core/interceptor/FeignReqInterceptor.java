package com.tca.core.interceptor;

import com.tca.core.config.RequestHolder;
import com.tca.core.constant.ProjectConfigConstant;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class FeignReqInterceptor implements RequestInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String HEADER_TRACE_ID = "trace";

    public FeignReqInterceptor() {
        logger.info("-----------------------------------业务Feign请求拦截器 {} 初始化完成！-----------------------------------", this.getClass().getName());
    }

    @Override
    public void apply(RequestTemplate template) {
        String url = template.url();
        logger.info("开始请求业务feign接口: {}", url);
        HttpServletRequest httpServletRequest = RequestHolder.getCurrenctRequest();
        if (httpServletRequest == null){
            logger.warn("httpServletRequest 为空!");
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
        logger.info("Feign-Method: {}，当前token信息为: {}", request.method(), token);
    }
}
