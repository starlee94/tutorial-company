package com.tca.utils.exception;

import com.tca.utils.constant.interfaces.BaseEnumIfc;
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