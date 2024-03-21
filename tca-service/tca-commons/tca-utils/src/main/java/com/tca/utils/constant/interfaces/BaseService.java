package com.tca.utils.constant.interfaces;


import com.tca.utils.exception.SurfaceException;

public interface BaseService<E extends Object, T extends Object> {


    /**
     * 处理业务的核心方法
     * @param reqParameter
     * @return
     * @throws Exception
     */
    T handle(E reqParameter) throws Exception;

    default void ex(BaseEnumIfc baseEnumIfc){
        throw new SurfaceException(baseEnumIfc);
    }
}
