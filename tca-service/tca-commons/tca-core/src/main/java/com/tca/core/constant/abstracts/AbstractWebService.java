package com.tca.core.constant.abstracts;


import com.tca.core.Response;
import com.tca.core.constant.finals.SpringBeanFactory;
import com.tca.core.constant.interfaces.BaseEnumIfc;
import com.tca.core.constant.interfaces.BaseService;
import com.tca.core.exception.LogicException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 各个业务应该继承的Service
 * @param <E> 请求体类型
 * @param <T> 返回体data类型
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */
public abstract class AbstractWebService<E extends Object, T extends Object> extends ProjectBase implements BaseService<E, Response<T>> {

    /**
     * 后续根据发展，可以在该类设置所有service共有的一些引用，如 HttpServletRequest 等。
     * 警告: 该类不写共有方法，只可以写共有引用！！！
     */
    @Autowired
    protected HttpServletRequest request;


    @Autowired
    protected HttpServletResponse response;


    /**
     * 子类需要实现的方法: 验证web端传参的合法性
     * @see AbstractWebService#handle(Object)
     * @param reqParameter
     * @throws Exception
     */
    protected abstract void validateParameter(E reqParameter) throws Exception;


    /**
     * 子类需要实现的方法: 处理业务逻辑
     * @see AbstractWebService#handle(Object)
     * @param reqParameter
     * @throws Exception
     */
    public abstract Response<T> process(E reqParameter) throws Exception;


    /**
     * 验证当前对象是否是代理类
     * @return
     */
    boolean isProxyClass(){
        return LOG == null;
    }


    @Override
    public Response<T> handle(E reqParameter) throws Exception {
        // 解决事务不生效问题 @Devin.qi
        AbstractWebService webService = SpringBeanFactory.getBean(this.getClass());
        webService.validateParameter(reqParameter);
        return webService.process(reqParameter);
    }

    @Override
    public void ex(BaseEnumIfc baseEnumIfc){
        throw new LogicException(baseEnumIfc);
    }
}
