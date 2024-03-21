package com.tca.utils.exception;

import feign.FeignException;
import lombok.Getter;

@Getter
public class FeignClientException extends FeignException {
    private boolean showError;

    private String msg;

    public FeignClientException(boolean showError, String msg) {
        super(999,msg);
        this.showError = showError;
        this.msg = msg;
    }
}
