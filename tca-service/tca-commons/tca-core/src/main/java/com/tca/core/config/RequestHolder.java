package com.tca.core.config;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于去缓存请求request
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */
public final class RequestHolder {


    private final static ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();


    public final static void init(HttpServletRequest request){
        RequestHolder.request.set(request);
    }


    public final static HttpServletRequest getCurrenctRequest(){
        return RequestHolder.request.get();
    }


    public final static void clean(){
        RequestHolder.request.remove();
    }

}
