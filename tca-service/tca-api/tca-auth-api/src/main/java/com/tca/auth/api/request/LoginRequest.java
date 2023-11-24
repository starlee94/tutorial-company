package com.tca.auth.api.request;

import com.tca.core.constant.abstracts.GlobalBaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author star.lee
 */
@Getter
@Setter
public class LoginRequest extends GlobalBaseRequest {

    private String username;
    private String secret;
}
