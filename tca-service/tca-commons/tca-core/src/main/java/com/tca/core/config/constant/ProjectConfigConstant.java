package com.tca.core.config.constant;

/**
 * 项目一些静态设置
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */

public interface ProjectConfigConstant {
    /**
     * requestHeader中用该字段去存储token
     */
    String TOKEN_HEADER_NAME = "Authorization";

    /**
     * 请求来源  APP or PC
     * default: PC
     */
    String REQUEST_SOURCE = "requestSource";
}
