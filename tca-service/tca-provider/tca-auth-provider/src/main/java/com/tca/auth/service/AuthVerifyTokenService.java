package com.tca.auth.service;

import com.tca.auth.abstracts.AbstractAuthService;
import com.tca.core.Response;
import com.tca.core.constant.enums.GlobalRequestEnum;
import com.tca.core.constant.enums.GlobalSystemEnum;
import com.tca.core.exception.LogicException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * @author star.lee
 */
@Service
public class AuthVerifyTokenService extends AbstractAuthService<String, Optional<String>> {

    @Override
    protected void validateParameter(String reqParameter) throws Exception {
        if(ObjectUtils.isEmpty(reqParameter)) {
            throw new LogicException(GlobalRequestEnum.PARAM_LOSS);
        }
    }

    @Override
    public Response<Optional<String>> process(String reqParameter) throws Exception {
        return Response.genResp(GlobalSystemEnum.OK, authMapper.verifyToken(reqParameter));
    }
}
