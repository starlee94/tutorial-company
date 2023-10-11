package com.tca.auth.service;

import com.tca.auth.abstracts.AbstractAuthService;
import com.tca.utils.Response;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends AbstractAuthService<Object, Object> {

    @Override
    protected void validateParameter(Object reqParameter) throws Exception { }

    @Override
    public Response<Object> process(Object reqParameter) throws Exception {

//        EmployeeDetail employeeDetail;
//
//        switch (reqParameter.getClass().getName()){
//            case "LoginRequest":
//                LoginRequest loginRequest = (LoginRequest) reqParameter;
//
//                authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                loginRequest.getUsername(),
//                                loginRequest.getSecret())
//                );
//
//                employeeDetail = empAccMapper.findEmpByUsername(loginRequest.getUsername());
//
//
//
//                break;
//        }

        return Response.builder()
                .code("200")
                .msg("test success")
                .build();
    }
}
