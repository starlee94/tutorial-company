package com.tca.utils.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 项目支持的所有 HttpRespStatus
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */
@AllArgsConstructor
@Getter
public enum HttpRespStatus {

    /**
     * 一切正常
     */
    OK(200),
    /**
     * 新资源已经被创建
     */
    @Deprecated
    CREATE_SUCCESS(201),
    /**
     * 资源删除成功
     */
    @Deprecated
    DELETE_SUCCESS(204),
    /**
     * 没有变化，客户端可以使用缓存数据
     */
    @Deprecated
    CAN_USE_CACHE(304),
    /**
     * Bad Request – 调用不合法，确切的错误应该在error payload中描述，例如：“JSON 不合法 ”
     */
    @Deprecated
    BAD_REQUEST(400),
    /**
     * 未认证，调用需要用户通过认证
     */
    @Deprecated
    NO_AUTH(401),
    /**
     * 不允许的，服务端正常解析和请求，但是调用被回绝或者不被允许
     */
    NO_SUPPORT(403),
    /**
     * 未找到，指定的资源不存在
     */
    @Deprecated
    NO_RESOURCE(404),
    /**
     * 不可指定的请求体 – 只有服务器不能处理实体时使用，比如图像不能被格式化，或者重要字段丢失。
     */
    @Deprecated
    PARAMETER_FAILER(422),
    /**
     * Internal Server Error – 标准服务端错误
     */
    @Deprecated
    SYS_ERROR(500);

    private int status;

}
