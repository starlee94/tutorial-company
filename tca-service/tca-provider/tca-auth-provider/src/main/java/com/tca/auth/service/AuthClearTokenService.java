package com.tca.auth.service;

import com.tca.auth.abstracts.AbstractAuthService;
import com.tca.core.Response;
import com.tca.core.constant.enums.GlobalRequestEnum;
import com.tca.core.constant.enums.GlobalSystemEnum;
import com.tca.core.entity.EmpAcc;
import com.tca.core.exception.LogicException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * @author star.lee
 */
@Service
public class AuthClearTokenService extends AbstractAuthService<String,Void> {

    @Autowired
    private AuthFindUsernameService authFindUsernameService;

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
            Optional<EmpAcc> employee = authFindUsernameService.process(username).getData();
            if(employee.isPresent()){
                authMapper.clearToken(employee.get().getId());
            }
            else{ globalSystemEnum = GlobalSystemEnum.SYSTEM_ERROR; }
        }
        else{
            globalSystemEnum = GlobalSystemEnum.SYSTEM_ERROR;
        }
        return Response.genResp(globalSystemEnum);
    }
}
