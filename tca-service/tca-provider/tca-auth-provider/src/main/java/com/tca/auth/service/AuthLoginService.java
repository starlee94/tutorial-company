package com.tca.auth.service;

import com.tca.auth.abstracts.AbstractAuthService;
import com.tca.auth.api.request.AuthLoginRequest;
import com.tca.core.Response;
import com.tca.core.constant.enums.GlobalRequestEnum;
import com.tca.core.constant.enums.GlobalSystemEnum;
import com.tca.core.entity.EmpAcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthLoginService extends AbstractAuthService<AuthLoginRequest, Void> {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    protected void validateParameter(AuthLoginRequest reqParameter) throws Exception {
        if(ObjectUtils.isEmpty(reqParameter)){ ex(GlobalRequestEnum.PARAM_LOSS); }
    }

    @Override
    public Response<Void> process(AuthLoginRequest reqParameter) throws Exception {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        reqParameter.getUsername(),
                        reqParameter.getSecret())
        );

        Optional<EmpAcc> empAcc = authMapper.findByUsername(reqParameter.getUsername());
        if(empAcc.isPresent() && empAcc.get().getPassword().equals(passwordEncoder.encode(reqParameter.getSecret()))){
            String token = jwtService.generateToken(new HashMap<>(), reqParameter.getUsername());
            authMapper.updateToken(empAcc.get().getId(), token);
            respMsg = String.format("Employee %s logged in.", reqParameter.getUsername());
            LOG.info(respMsg);
            return Response.genResp(GlobalSystemEnum.OK, respMsg);
        }

        return Response.genResp(GlobalSystemEnum.SYSTEM_ERROR);
    }


}
