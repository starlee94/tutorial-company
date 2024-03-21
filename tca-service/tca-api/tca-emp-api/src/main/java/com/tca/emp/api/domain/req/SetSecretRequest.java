package com.tca.emp.api.domain.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SetSecretRequest {
    private String username;
    private String secret;
}
