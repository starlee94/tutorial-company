package com.tca.auth.service.token;

import com.tca.auth.abstracts.AbstractAuthService;
import com.tca.utils.Response;
import com.tca.utils.constant.enums.GlobalRequestEnum;
import com.tca.utils.exception.LogicException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author star.lee
 */
@Service
public class AuthVerifyTokenService extends AbstractAuthService<String, String> {

    @Override
    protected void validateParameter(String reqParameter) throws Exception {
        if(ObjectUtils.isEmpty(reqParameter)) {
            throw new LogicException(GlobalRequestEnum.PARAM_LOSS);
        }
    }

    @Override
    public Response<String> process(String reqParameter) throws Exception {
        return Response.genResp(authMapper.verifyToken(reqParameter));
    }
}
