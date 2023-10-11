package com.tca.utils.enums;

import com.tca.utils.interfaces.BaseEnumIfc;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum GlobalSystemEnum implements BaseEnumIfc {

    OK("MSCS0000", "成功"),
    SYSTEM_ERROR("MSCE9999", "失败"),
    URI_NOT_EXIST("MSCE9998", "接口不存在！"),
    NO_EXCEPTION_INFO("MSCE9997", "没有获取到异常详细信息！"),
    FEIGN_ERROR("MSCE9996", "Feign调用出现异常！"),
    ;


    private final String code;

    private final String msg;

    @Override
    public String getRspCode() { return code; }

    @Override
    public String getRspMsg() { return msg; }
}
