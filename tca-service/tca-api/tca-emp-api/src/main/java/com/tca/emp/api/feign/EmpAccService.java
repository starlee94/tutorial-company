package com.tca.emp.api.feign;

import com.tca.emp.api.vo.EmployeeDetail;
import com.tca.utils.interfaces.ServiceName;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@FeignClient(value = "${feign_emp_service:" + ServiceName.EMP_SERVICE + "-${spring.profiles.active}}", url = "${service.emp.url}")
@Headers({"Content-Type: application/json", "Accept: application/json"})
public interface EmpAccService {

    @GetMapping("/emp/getUsername")
    EmployeeDetail findEmpByUsername(String username);

    @GetMapping("emp/verifyToken")
    Optional<String> verifyToken (String token);

    @GetMapping("emp/clearToken")
    int clearToken(String token);

}
