package com.tca.core.exception;

import com.tca.core.constant.interfaces.BaseEnumIfc;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author star.lee
 */
@AllArgsConstructor
@Getter
public class LogicException extends RuntimeException{

    private BaseEnumIfc baseEnumIfc;
}