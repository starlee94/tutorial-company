package com.tca.emp.service;

import com.tca.emp.abstracts.AbstractEmpService;
import com.tca.emp.api.domain.po.CreateEmpPo;
import com.tca.emp.api.domain.req.CreateEmpRequest;
import com.tca.emp.api.domain.req.SetSecretRequest;
import com.tca.emp.api.domain.req.SetStatusRequest;
import com.tca.emp.api.domain.req.SetTagRequest;
import com.tca.utils.Response;
import com.tca.utils.constant.enums.GlobalRequestEnum;
import com.tca.utils.constant.enums.GlobalSystemEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
public class EmpAccActionService extends AbstractEmpService<Object, Object> {

    @Autowired
    PasswordEncoder passwordEncoder;

    Boolean testVal = Boolean.FALSE;

    @Override
    protected void validateParameter(Object reqParameter) throws Exception {
        if(ObjectUtils.isEmpty(reqParameter)){ ex(GlobalRequestEnum.PARAM_LOSS); }
        testVal =  Boolean.TRUE;
    }

    @Override
    public Response<Object> process(Object reqParameter) throws Exception {
        return null;
    }

    public Response<Void> createEmployee(CreateEmpRequest createEmpRequest) throws Exception {
        validateParameter(createEmpRequest);
        if(!validatePermission()) { return Response.genResp(GlobalSystemEnum.PERMISSION_ERROR); }

        LOG.info(testVal ? "test1{}" : "test2");

        Boolean checkAvailability = ObjectUtils.isEmpty(empAccMapper.findEmpByUsername(createEmpRequest.getUsername())) ? Boolean.TRUE : Boolean.FALSE;
        if(checkAvailability){
            String secret = passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(15));
            CreateEmpPo createEmpPo = CreateEmpPo.init(createEmpRequest, secret);
            empAccMapper.createEmployee(createEmpPo);
        }
        else {
            return Response.genFailResp();
        }

        return Response.genSuccessResp();
    }

    public Response<Void> setTag(SetTagRequest setTagRequest) throws Exception{
        validateParameter(setTagRequest);
        if(!validatePermission()) { return Response.genResp(GlobalSystemEnum.PERMISSION_ERROR); }

        empAccMapper.setTag(setTagRequest.getEmployeeId(), setTagRequest.getTagId(), new Date());
        return Response.genSuccessResp();
    }

    public Response<Void> setStatus(SetStatusRequest setStatusRequest) throws Exception{
        validateParameter(setStatusRequest);
        if(!validatePermission()) { return Response.genResp(GlobalSystemEnum.PERMISSION_ERROR); }

        empAccMapper.setStatus(setStatusRequest.getEmployeeId(), setStatusRequest.getStatus(), new Date());
        return Response.genSuccessResp();
    }

    public Response<Void> setSecret(SetSecretRequest setSecretRequest) throws Exception{
        validateParameter(setSecretRequest);

        String secret = passwordEncoder.encode(setSecretRequest.getSecret());
        Boolean verify = empAccMapper.verifySecret(secret) > 0 ? Boolean.FALSE : Boolean.TRUE;
        if(verify) { empAccMapper.setSecret(setSecretRequest.getUsername(), secret); }
        else{
            return Response.genResp(GlobalSystemEnum.PASSWORD_EXISTS);
        }
        return Response.genSuccessResp();
    }
}
