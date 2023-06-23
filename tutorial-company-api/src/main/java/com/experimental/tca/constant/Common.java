package com.experimental.tca.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author star.lee
 */

@Getter
@AllArgsConstructor
public enum Common {
    LOG_START("--------------------------------------Log Start--------------------------------------"),
    LOG_END("---------------------------------------Log End---------------------------------------"),
    ERROR_MSG("(Code:%s, Message:\"%s\")"),
    EMPLOYEE("Employee");

    private final String msg;
}
