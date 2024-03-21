package com.tca.utils.constant.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author star.lee
 */
@Getter
@RequiredArgsConstructor
@Slf4j
public enum AddressUtil {

    ADDR_SWAGGER(new String[]{
            // -- Swagger UI v2
//            "/v2/api-docs",
//            "/swagger-resources",
//            "/swagger-resources/**",
//            "/configuration/ui",
//            "/configuration/security",
//            "/swagger-ui.html",
//            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
    }),

    ADDR_AUTH(new String[]{
            "/auth/**"
    }),

    ADDR_EMP(new String[]{
            "/emp/test"
    }),

    ADDR_OTHER(new String[]{
            // other public endpoints of your API may be appended to this array
    })
    ;


    private final String[] address;

    public static String[] fullAddress(){
        List<String> addressList = new ArrayList<>();
        Arrays.stream(AddressUtil.values())
                .filter(addressUtil -> ! "ADDR_FULL".equals(addressUtil.name()))
                .map(AddressUtil::getAddress).forEach(element ->{
                    addressList.addAll(Arrays.asList(element));
                });
        return addressList.toArray(new String[0]);
    }

}
