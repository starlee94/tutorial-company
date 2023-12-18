package com.tca.auth.service;

import com.tca.auth.abstracts.AbstractAuthService;
import com.tca.core.Response;
import com.tca.core.constant.enums.GlobalRequestEnum;
import com.tca.core.constant.enums.GlobalSystemEnum;
import com.tca.core.entity.EmpAcc;
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
        return Response.genResp(GlobalSystemEnum.OK, authMapper.findByUsername(reqParameter));
    }
}
