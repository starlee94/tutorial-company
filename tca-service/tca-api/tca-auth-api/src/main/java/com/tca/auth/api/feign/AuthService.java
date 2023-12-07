package com.tca.auth.api.feign;

import com.tca.core.service.CommonService;
import com.tca.utils.constant.interfaces.ServiceName;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author star.lee
 */
@FeignClient(value = "${feign_auth_service:" + ServiceName.AUTH_SERVICE + "-${spring.profiles.active}}", url = "${service.auth.url}")
public interface AuthService extends CommonService {


}
