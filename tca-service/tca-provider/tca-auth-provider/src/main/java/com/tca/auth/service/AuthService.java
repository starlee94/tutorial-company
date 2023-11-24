package com.tca.auth.service;

import com.tca.auth.abstracts.AbstractAuthService;
import com.tca.core.Response;
import com.tca.core.constant.enums.GlobalRequestEnum;
import com.tca.core.constant.enums.GlobalSystemEnum;
//import com.tca.core.entity.EmpAcc;
import com.tca.core.exception.LogicException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * @author star.lee
 */
@Service
public class AuthService extends AbstractAuthService<Object, Object> {

    @Override
    protected void validateParameter(Object reqParameter) throws Exception {
        if(ObjectUtils.isEmpty(reqParameter)) {
            throw new LogicException(GlobalRequestEnum.PARAM_LOSS);
        }
    }

    @Override
    public Response<Object> process(Object reqParameter) throws Exception {
        return Response.genResp(GlobalSystemEnum.OK);
    }

    public Response<Void> test() throws Exception{ return Response.genResp(GlobalSystemEnum.OK); }

//    public Response<Optional<EmpAcc>> findByUsername(String username) throws Exception {
//        validateParameter(username);
//        return Response.genResp(GlobalSystemEnum.OK, authMapper.findByUsername(username));
//    }

    public Response<Optional<String>> verifyToken(String token) throws Exception {
        validateParameter(token);
        return Response.genResp(GlobalSystemEnum.OK, authMapper.verifyToken(token));
    }

    public Response<Void> clearToken(String token) throws Exception {
        validateParameter(token);
        GlobalSystemEnum globalSystemEnum = GlobalSystemEnum.OK;
//
//        String username = jwtService.extractUsername(token);
//        if(StringUtils.isNotEmpty(username)){
//            Optional<EmpAcc> employee = findByUsername(username).getData();
//            if(employee.isPresent()){
//               authMapper.clearToken(employee.get().getId());
//            }
//            else{ globalSystemEnum = GlobalSystemEnum.SYSTEM_ERROR; }
//        }
//        else{
//            globalSystemEnum = GlobalSystemEnum.SYSTEM_ERROR;
//        }
        return Response.genResp(globalSystemEnum);
    }
}
