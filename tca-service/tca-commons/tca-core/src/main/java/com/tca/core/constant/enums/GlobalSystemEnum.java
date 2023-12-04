package com.tca.core.constant.enums;

import com.tca.core.constant.interfaces.BaseEnumIfc;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum GlobalSystemEnum implements BaseEnumIfc {

    OK("MSCS0000", "SUCCESS"),
    SYSTEM_ERROR("MSCE9999", "FATAL ERROR"),
    URI_NOT_EXIST("MSCE9998", "API DOES NOT EXIST!"),
    NO_EXCEPTION_INFO("MSCE9997", "UNABLE TO ACQUIRE EXCEPTION MESSAGE!"),
    FEIGN_ERROR("MSCE9996", "Feign EXCEPTION OCCURRED!"),
    ;


    private final String code;

    private final String msg;

    @Override
    public String getRspCode() { return code; }

    @Override
    public String getRspMsg() { return msg; }
}
