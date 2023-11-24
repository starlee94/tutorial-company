package com.tca.core.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * @author star.lee
 */

public interface  CommonService {

//    @GetMapping("/auth/get/username")
//    Optional<EmpAcc> findByUsername(String username);

    @GetMapping("/auth/token/verify")
    Optional<String> verifyToken (@RequestParam("token") String token);

    @GetMapping("/auth/token/clear")
    Void clearToken(@RequestParam("token") String token);

}
