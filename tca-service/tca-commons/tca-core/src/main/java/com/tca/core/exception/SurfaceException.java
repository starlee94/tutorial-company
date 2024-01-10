package com.tca.core.exception;

import com.tca.core.constant.interfaces.BaseEnumIfc;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SurfaceException extends RuntimeException{

    private BaseEnumIfc baseEnumIfc;
}
