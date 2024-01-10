package com.tca.auth.api.request;

import lombok.Data;

/**
 * @author star.lee
 */
@Data
public class AuthLoginRequest {

    private String username;
    private String secret;
}
