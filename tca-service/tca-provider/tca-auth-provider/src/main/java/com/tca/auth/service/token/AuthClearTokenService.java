package com.tca.auth.service.token;

import com.tca.auth.abstracts.AbstractAuthService;
import com.tca.auth.service.AuthQueryService;
import com.tca.core.entity.EmpAcc;
import com.tca.utils.Response;
import com.tca.utils.constant.enums.GlobalRequestEnum;
import com.tca.utils.constant.enums.GlobalSystemEnum;
import com.tca.utils.exception.LogicException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author star.lee
 */
@Service
public class AuthClearTokenService extends AbstractAuthService<String,Void> {

    @Autowired
    private AuthQueryService authQueryService;

    @Override
    protected void validateParameter(String reqParameter) throws Exception {
        if(ObjectUtils.isEmpty(reqParameter)) {
            throw new LogicException(GlobalRequestEnum.PARAM_LOSS);
        }
    }

    @Override
    public Response<Void> process(String reqParameter) throws Exception {
        GlobalSystemEnum globalSystemEnum = GlobalSystemEnum.OK;

        String username = jwtService.extractUsername(reqParameter);
        if(StringUtils.isNotEmpty(username)){
            EmpAcc empAcc = authQueryService.process(username).getData();
            if(!ObjectUtils.isEmpty(empAcc)){
                authMapper.clearToken(empAcc.getId());
            }
            else{ globalSystemEnum = GlobalSystemEnum.SYSTEM_ERROR; }
        }
        else{
            globalSystemEnum = GlobalSystemEnum.SYSTEM_ERROR;
        }
        return Response.genResp(globalSystemEnum);
    }
}
