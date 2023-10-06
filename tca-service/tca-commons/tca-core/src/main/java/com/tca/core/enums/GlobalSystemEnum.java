package com.tca.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 全球商业枚举
 * 公共对外开放的业务异常
 *
 * @author jasonqian
 * @date 2023/08/30
 */

@Getter
@AllArgsConstructor
public enum GlobalSystemEnum {

    OK("MSCS0000", "成功"),
    SYSTEM_ERROR("MSCE9999", "失败"),
    URI_NOT_EXIST("MSCE9998", "接口不存在！"),
    NO_EXCEPTION_INFO("MSCE9997", "没有获取到异常详细信息！"),
    FEIGN_ERROR("MSCE9996", "Feign调用出现异常！"),
    ;


    private final String errorCode;

    private final String errorMsg;
}
