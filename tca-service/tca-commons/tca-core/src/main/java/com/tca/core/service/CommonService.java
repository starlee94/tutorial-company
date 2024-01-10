package com.tca.core.service;

import com.tca.core.entity.EmpAcc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author star.lee
 */

public interface CommonService {

    @GetMapping("/auth/get/username")
    EmpAcc findByUsername(@RequestParam("username") String username);

    @GetMapping("/auth/token/verify")
    String verifyToken(@RequestParam("token") String token);

    @GetMapping("/auth/token/clear")
    Void clearToken(@RequestParam("token") String token);

}
