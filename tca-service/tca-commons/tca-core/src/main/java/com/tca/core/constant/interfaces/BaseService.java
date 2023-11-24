package com.tca.core.constant.interfaces;

public interface BaseService<E extends Object, T extends Object> {


    /**
     * 处理业务的核心方法
     * @param reqParameter
     * @return
     * @throws Exception
     */
    T handle(E reqParameter) throws Exception;

}
