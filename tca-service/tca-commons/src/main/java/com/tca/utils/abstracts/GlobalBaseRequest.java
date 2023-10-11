package com.tca.utils.abstracts;

import com.google.gson.Gson;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 全局基础Req，提供共有tostring。
 * 建议业务中的Req都继承该类
 */
public abstract class GlobalBaseRequest implements Serializable {


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    /**
     * JavaBean 转换 JavaBean
     * @param t
     * @param <T>
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <T> T convertBean(Class<T> t) throws IntrospectionException, InvocationTargetException, IllegalAccessException, InstantiationException {
        BeanInfo beanInfo = Introspector.getBeanInfo(this.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        T newInstance = t.newInstance();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if ("class".equals(key)) {
                continue;
            }
            if(!haveDescriptor(t, key)){
                continue;
            }
            Method getter = property.getReadMethod();
            if(getter == null){
                continue;
            }
            Object value = getter.invoke(this);
            PropertyDescriptor pd = new PropertyDescriptor(key, t);
            pd.getWriteMethod().invoke(newInstance, value);
        }
        return newInstance;
    }

    private <T> boolean haveDescriptor(Class<T> t, String fieldName){
        Field[] fields = t.getDeclaredFields();
        for(Field field : fields){
            if(field.getName().equals(fieldName)){
                return true;
            }
        }
        return false;
    }

}
