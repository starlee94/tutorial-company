package com.tca.utils.config;

import brave.Tracer;
import com.tca.utils.Response;
import com.tca.utils.constant.abstracts.ProjectBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author star.lee
 */
@ControllerAdvice
public class ResponseTraceIdConfiguration extends ProjectBase implements ResponseBodyAdvice {

    @Autowired
    Tracer tracer;
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof Response){
            try {
                Response response = (Response) o;
                response.setTraceId(tracer.currentSpan().context().traceIdString());
                return response;
            }catch (Exception e){
                LOG.error("[RespTraceIdConfiguration] [beforeBodyWrite] transfer trace id fail", e);
                return o;
            }
        }
        return o;
    }
}