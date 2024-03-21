package com.tca.emp.api.feign;

import com.tca.utils.constant.interfaces.ServiceName;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author star.lee
 */
@FeignClient(value = "${feign_emp_service:" + ServiceName.EMP_SERVICE + "-${spring.profiles.active}}", url = "${service.emp.url}")
@Headers({"Content-Type: application/json", "Accept: application/json"})
public interface EmpAccService {

//    @PutMapping("/emp/set/secret")
//    void setSecret(@RequestBody SetSecretRequest setSecretRequest);

}
