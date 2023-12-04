package com.tca.core.service;

import com.tca.core.entity.EmpAcc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * @author star.lee
 */

public interface CommonService {

    @RequestMapping("/auth/get/username")
    Optional<EmpAcc> findByUsername(String username);

    @GetMapping("/auth/token/verify")
    Optional<String> verifyToken(@RequestParam("token") String token);

    @GetMapping("/auth/token/clear")
    Void clearToken(@RequestParam("token") String token);

}
