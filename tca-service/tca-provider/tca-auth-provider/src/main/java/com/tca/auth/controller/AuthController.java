package com.tca.auth.controller;

import com.tca.auth.service.AuthClearTokenService;
import com.tca.auth.service.AuthFindUsernameService;
import com.tca.auth.service.AuthVerifyTokenService;
import com.tca.core.Response;
import com.tca.core.constant.abstracts.AbstractWebController;
import com.tca.core.constant.enums.GlobalSystemEnum;
import com.tca.core.entity.EmpAcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/get/username")
    public Response<Optional<EmpAcc>> findByUsername(String username) { return handle(authFindUsernameService, username); }


    @Autowired
    AuthVerifyTokenService authVerifyTokenService;

    @GetMapping("/token/verify")
    public Response<Optional<String>> verifyToken(@RequestParam("token") String token) { return handle(authVerifyTokenService, token); }


    @Autowired
    AuthClearTokenService authClearTokenService;

    @GetMapping("/token/clear")
    public Response<Void> clearToken(@RequestParam("token") String token) { return handle(authClearTokenService, token); }

}