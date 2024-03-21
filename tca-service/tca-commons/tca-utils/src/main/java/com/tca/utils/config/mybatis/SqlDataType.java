package com.tca.utils.config.mybatis;


/**
 * 数据权限控制类型
 * @author Devin.qi < devin.qi@ixsecurities.com />
 * @see SqlDataPermission
 * @see SqlDataMapping
 * @see SqlDataInterceptor
 */
public enum SqlDataType {


    /**
     * 只用用户关系控制
     */
    ONLY_USER,

    /**
     * 同时用用户关系和账号关系控制
     */
    BOTH_USER_ACCOUNT,
    ;




}
