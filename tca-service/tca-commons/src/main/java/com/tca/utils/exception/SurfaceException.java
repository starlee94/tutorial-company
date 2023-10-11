package com.tca.utils.exception;

import com.tca.utils.interfaces.BaseEnumIfc;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SurfaceException extends RuntimeException{

    private BaseEnumIfc baseEnumIfc;
}
