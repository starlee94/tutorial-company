package com.tca.auth.controller;

import com.tca.auth.service.AuthService;
import com.tca.core.Response;
import com.tca.core.constant.abstracts.AbstractWebController;
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

    @Autowired
    AuthService authService;

    @GetMapping("/test")
    public Response<Void> testAuth() { return authService.test(); }
//
//    @GetMapping("/get/username")
//    public Response<Optional<EmpAcc>> findByUsername(String username) throws Exception { return authService.findByUsername(username); }

    @GetMapping("/token/verify")
    public Response<Optional<String>> verifyToken(@RequestParam("token") String token) throws Exception {
        return authService.verifyToken(token);
    }

    @GetMapping("/token/clear")
    public Response<Void> clearToken(@RequestParam("token") String token) throws Exception {
        return authService.clearToken(token);
    }

    ;

}