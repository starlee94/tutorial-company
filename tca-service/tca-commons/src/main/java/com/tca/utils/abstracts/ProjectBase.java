package com.tca.utils.abstracts;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目顶级父类，公共限制
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */
abstract class ProjectBase {

    /**
     * 统一提供logger
     */
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 序列化
     * Object转为String
     * @param obj
     * @return
     */
    protected String toJSONString(Object obj){ return new Gson().toJson(obj); }


    /**
     * 反序列化
     * String转Object
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T> T parseObject(String text, Class<T> clazz){ return new Gson().fromJson(text, clazz); }

}
