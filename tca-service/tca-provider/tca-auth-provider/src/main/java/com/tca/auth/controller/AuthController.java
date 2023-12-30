package com.tca.auth.controller;

import com.tca.auth.api.request.AuthLoginRequest;
import com.tca.auth.service.AuthLoginService;
import com.tca.auth.service.AuthQueryService;
import com.tca.auth.service.token.AuthClearTokenService;
import com.tca.auth.service.token.AuthVerifyTokenService;
import com.tca.core.Response;
import com.tca.core.constant.abstracts.AbstractWebController;
import com.tca.core.constant.enums.GlobalSystemEnum;
import com.tca.core.entity.EmpAcc;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author star.lee
 */
@RestController
@RequestMapping("/auth")
public class AuthController extends AbstractWebController {

    @GetMapping("/info")
    public EmpAcc getEmployeeInfo(@AuthenticationPrincipal UserDetails userDetails){
        EmpAcc empAcc = (EmpAcc) userDetails;
        if (ObjectUtils.isEmpty(empAcc)) {
            LOG.warn("Header Token is empty, fail to acquire employee info!");
            return null;
        }
        return empAcc;
    }

    @GetMapping("/test")
    public Response<Void> testAuth() { return Response.genResp(GlobalSystemEnum.OK,"success access."); }

    @Autowired
    AuthQueryService authQueryService;

    @Hidden
    @RequestMapping("/get/username")
    public Response<EmpAcc> findByUsername(String username) { return handle(authQueryService, username); }

    @Autowired
    AuthVerifyTokenService authVerifyTokenService;

    @Hidden
    @GetMapping("/token/verify")
    public Response<String> verifyToken(@RequestParam("token") String token) { return handle(authVerifyTokenService, token); }


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