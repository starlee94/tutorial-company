package com.tca.auth.controller;

import com.tca.auth.api.request.LoginRequest;
import com.tca.auth.service.AuthService;
import com.tca.utils.Response;
import com.tca.utils.abstracts.AbstractWebController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController extends AbstractWebController {

    @Autowired
    AuthService authService;

    @GetMapping("/test")
    public Response<Object> testAuth(LoginRequest loginRequest){
        return handle(authService, loginRequest);
    }

}