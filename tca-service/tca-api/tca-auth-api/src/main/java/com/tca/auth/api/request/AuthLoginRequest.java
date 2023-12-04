package com.tca.auth.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author star.lee
 */
@Data
public class AuthLoginRequest {

    @JsonProperty
    private String username;
    @JsonProperty
    private String secret;
}
