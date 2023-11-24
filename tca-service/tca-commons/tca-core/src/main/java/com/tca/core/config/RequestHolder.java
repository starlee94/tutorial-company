package com.tca.core.config;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于去缓存请求request
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */
public final class RequestHolder {


    private final static ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<>();


    public static void init(HttpServletRequest request){
        RequestHolder.REQUEST.set(request);
    }


    public static HttpServletRequest getCurrenctRequest(){
        return RequestHolder.REQUEST.get();
    }


    public static void clean(){
        RequestHolder.REQUEST.remove();
    }

}
