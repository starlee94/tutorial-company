package com.tca.auth.api.request;

import com.tca.utils.abstracts.GlobalBaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest extends GlobalBaseRequest {

    private String username;
    private String secret;
}
