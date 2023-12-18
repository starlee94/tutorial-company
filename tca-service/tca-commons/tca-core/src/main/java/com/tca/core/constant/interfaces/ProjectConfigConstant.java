package com.tca.core.constant.interfaces;

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
     * 用于存放sql-data的header名称
     */
    String SQL_DATA_HEADER = "SqlData";

    /**
     * 请求来源  APP or PC
     * default: PC
     */
    String REQUEST_SOURCE = "requestSource";

    /**
     * 用于auth中的登录标识
     */
    String INNER_REQ_HEADER = "Inner_Sign";
}
