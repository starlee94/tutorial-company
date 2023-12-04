package com.tca.core.service;

import com.tca.core.Response;
import com.tca.core.constant.abstracts.AbstractWebService;
import com.tca.core.constant.enums.GlobalRequestEnum;
import com.tca.core.entity.EmpAcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class AuthEmpService extends AbstractWebService<String, Void> implements UserDetailsService {

    @Autowired
    CommonService commonService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<EmpAcc> empAcc = commonService.findByUsername(username);
        return empAcc.orElse(null);
    }

    @Override
    protected void validateParameter(String reqParameter) throws Exception {
        if(StringUtils.isEmpty(reqParameter)) {ex(GlobalRequestEnum.PARAM_LOSS);}
    }

    @Override
    public Response<Void> process(String reqParameter) throws Exception {
        return null;
    }
}
