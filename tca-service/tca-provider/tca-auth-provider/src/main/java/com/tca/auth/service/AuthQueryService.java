package com.tca.auth.service;

import com.tca.auth.abstracts.AbstractAuthService;
import com.tca.core.entity.EmpAcc;
import com.tca.utils.Response;
import com.tca.utils.constant.enums.GlobalRequestEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author star.lee
 */
@Service
public class AuthQueryService extends AbstractAuthService<String, EmpAcc> {

    @Override
    protected void validateParameter(String reqParameter) throws Exception {
        if(ObjectUtils.isEmpty(reqParameter)) {
            ex(GlobalRequestEnum.PARAM_LOSS);
        }
    }

    @Override
    public Response<EmpAcc> process(String reqParameter) throws Exception {
        return Response.genResp(authMapper.findByUsername(reqParameter));
    }
}
