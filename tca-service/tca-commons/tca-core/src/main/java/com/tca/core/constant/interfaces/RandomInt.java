package com.tca.core.constant.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author star.lee
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RandomInt {
    int min();

    int max();
}
