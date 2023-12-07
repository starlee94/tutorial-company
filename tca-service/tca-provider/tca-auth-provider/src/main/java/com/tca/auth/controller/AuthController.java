package com.tca.auth.controller;

import com.tca.auth.api.request.AuthLoginRequest;
import com.tca.auth.service.AuthClearTokenService;
import com.tca.auth.service.AuthFindUsernameService;
import com.tca.auth.service.AuthLoginService;
import com.tca.auth.service.AuthVerifyTokenService;
import com.tca.core.Response;
import com.tca.core.constant.abstracts.AbstractWebController;
import com.tca.core.constant.enums.GlobalSystemEnum;
import com.tca.core.entity.EmpAcc;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author star.lee
 */
@RestController
@RequestMapping("/auth")
public class AuthController extends AbstractWebController {

    @GetMapping("/test")
    public Response<Void> testAuth() { return Response.genResp(GlobalSystemEnum.OK,"success access."); }

    @Autowired
    AuthFindUsernameService authFindUsernameService;


    @Hidden
    @RequestMapping("/get/username")
    public Response<Optional<EmpAcc>> findByUsername(String username) { return handle(authFindUsernameService, username); }


    @Autowired
    AuthVerifyTokenService authVerifyTokenService;

    @Hidden
    @GetMapping("/token/verify")
    public Response<Optional<String>> verifyToken(@RequestParam("token") String token) { return handle(authVerifyTokenService, token); }


    @Autowired
    AuthClearTokenService authClearTokenService;

    @Hidden
    @GetMapping("/token/clear")
    public Response<Void> clearToken(@RequestParam("token") String token) { return handle(authClearTokenService, token); }

    @Autowired
    AuthLoginService authLoginService;

    @PostMapping("/login")
    public Response<Void> login(@RequestBody AuthLoginRequest authLoginRequest){ return handle(authLoginService,authLoginRequest); }

}