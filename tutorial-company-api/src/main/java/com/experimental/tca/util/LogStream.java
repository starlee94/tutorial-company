package com.experimental.tca.util;

import com.experimental.tca.constant.Common;
import com.experimental.tca.constant.ResultCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author star.lee
 */
@Slf4j
public enum LogStream {
    ;

    public static void start(){
        log.info(Common.LOG_START.getMsg());
    }

    public static void body(String msg){
        log.info(msg);
    }

    public static void error(ResultCode resultCode){
        log.error(String.format(Common.ERROR_MSG.getMsg(), resultCode.getCode(), resultCode.getMessage()));
        end();
    }

    public static void end(){
        log.info(Common.LOG_END.getMsg());
    }
}
