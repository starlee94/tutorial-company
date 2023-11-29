package com.tca.core.constant.abstracts;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;


/**
 * 项目顶级父类，公共限制
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */
@Slf4j
public abstract class ProjectBase {

    protected Logger LOG = log;

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
