package com.tca.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.tca.core.constant.enums.HttpRespStatus;
import com.tca.core.constant.interfaces.BaseEnumIfc;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

/**
 * 返回体信息
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */
@Getter
@Setter
@ApiModel(value = "Response")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 4768835308338670054L;

    @ApiModelProperty(value = "code", name = "code")
    private String code;

    @ApiModelProperty(value = "message", name = "message")
    private String msg;

    @ApiModelProperty(value = "data", name = "data")
    private T data;

    HttpRespStatus httpRespStatus;

    public static Response parseResp(String respStr){
        Map<String, Object> respMap = new Gson().fromJson(respStr, Map.class);
        return Response.builder()
                .code(respMap.get("code").toString())
                .msg(respMap.get("msg").toString())
                .data(respMap.get("data"))
                .build();
    }

    public static Response genResp(BaseEnumIfc baseEnumIfc){

        return Response.builder()
                .code(baseEnumIfc.getRspCode())
                .msg(baseEnumIfc.getRspMsg())
                .build();
    }

    public static Response genResp(BaseEnumIfc baseEnumIfc, Object data){

        return Response.builder()
                .code(baseEnumIfc.getRspCode())
                .msg(baseEnumIfc.getRspMsg())
                .data(data)
                .httpRespStatus(HttpRespStatus.OK)
                .build();
    }
}

