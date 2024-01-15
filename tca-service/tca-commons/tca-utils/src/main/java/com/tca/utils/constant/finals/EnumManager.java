package com.tca.utils.constant.finals;

import com.tca.utils.constant.interfaces.IndexEnumIfc;

/**
 * 工具类，提供了全局的筛选ENUM方法
 */
public final class EnumManager {


    /**
     * 获取通过index获取一个指定类型的Enum
     * @param index
     * @param clazz
     * @param <T>
     * @return
     */
    public final static <T extends IndexEnumIfc> T getIndexEnumByIndex(int index, Class<T> clazz){
        if (!clazz.isEnum()){
            throw new UnsupportedOperationException(String.format("该方法只支持以 IndexEnumIfc 为父级的ENUM类型！传入参数: %s, %s", index, clazz.getName()));
        }
        T[] enumConstants = clazz.getEnumConstants();
        if (enumConstants == null || enumConstants.length < 1){
            throw new NullPointerException(clazz + " 类型的Enum没有找到元素列表！");
        }
        for (T t:enumConstants){
            if (t.getIndex() == index){
                return t;
            }
        }
        return null;
    }

}
