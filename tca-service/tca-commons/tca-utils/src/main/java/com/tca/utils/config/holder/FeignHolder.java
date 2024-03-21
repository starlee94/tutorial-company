package com.tca.utils.config.holder;


/**
 * 用于去存储feign的一些公共变量，而实现自定义调用feign
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */
public final class FeignHolder {

    /**
     * 是否需要将远程的业务异常返回给前端
     */
    private final static ThreadLocal<Boolean> returnRemoteErr = new ThreadLocal<>();


    /**
     * 让下一次请求允许返回远程的业务异常
     */
    public final static void beReturnRemoteErr(){
        returnRemoteErr.set(true);
    }

    /**
     * 受保护方法，查看是否需要返回远程的业务异常信息
     * @return
     */
    public final static boolean canReturnRemoteErr(){
        Boolean canReturn = returnRemoteErr.get();
        return canReturn != null && canReturn;
    }

    /**
     * 受保护方法，清空缓存
     */
    public final static void cleanReturnRemoteErr(){
        returnRemoteErr.remove();
    }

}
