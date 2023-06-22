package com.experimental.tca.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author star.lee
 */

@Getter
@AllArgsConstructor
public enum Message {
    EMPLOYEE("Employee");

    private final String msg;
}
