package com.tca.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tca.core.enums.HttpRespStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 返回体信息
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */
@Getter
@Setter
@ApiModel(value = "返回体")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 4768835308338670054L;

    @ApiModelProperty(value = "状态码", name = "状态码")
    private String code;

    @ApiModelProperty(value = "状态码描述", name = "状态码描述")
    private String msg;

    @ApiModelProperty(value = "数据对象", name = "数据对象")
    private T data;
    /**
     * 该字段不对外设置，用于记录业务处理成功时采用的http-status
     */
    HttpRespStatus httpRespStatus;
}

