package com.experimental.tca.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author star.lee
 */

@Getter
@AllArgsConstructor
public enum SecurityEnum {

    SECRET_KEY("782F4125442A472D4B6150645367566B59703373367639792442264528482B4D"),
    MINUTES("120")
    ;

    private final String data;
}
