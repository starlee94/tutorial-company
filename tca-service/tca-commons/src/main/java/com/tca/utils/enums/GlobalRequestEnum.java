package com.tca.utils.enums;

import com.tca.utils.interfaces.BaseEnumIfc;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GlobalRequestEnum implements BaseEnumIfc {

    PARAM_LOSS("GRE0001", "Missing Parameter！"),
    PARAM_UNSUPPORT("GRE0002", "Parameter type invalid！");

    private final String errorCode;

    private final String errorMsg;

    @Override
    public String getRspCode() { return errorCode; }

    @Override
    public String getRspMsg() { return errorMsg; }
}
