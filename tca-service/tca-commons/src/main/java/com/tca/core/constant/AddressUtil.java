package com.tca.core.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author star.lee
 */
@Getter
@RequiredArgsConstructor
public enum AddressUtil {

    AUTH_SWAGGER(new String[]{
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
    }),

    AUTH_OTHER(new String[]{
            // other public endpoints of your API may be appended to this array
            "/auth/**"
    }),

    AUTH_FULL(ArrayUtils.addAll(
            AUTH_SWAGGER.getAddress(),
            AUTH_OTHER.getAddress()))
    ;


    private final String[] address;

}
