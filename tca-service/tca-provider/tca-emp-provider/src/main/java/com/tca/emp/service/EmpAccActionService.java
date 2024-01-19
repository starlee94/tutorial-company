package com.tca.emp.service;

import com.tca.core.Response;
import com.tca.core.constant.enums.GlobalRequestEnum;
import com.tca.core.constant.enums.GlobalSystemEnum;
import com.tca.emp.abstracts.AbstractEmpService;
import com.tca.emp.api.domain.po.CreateEmpPo;
import com.tca.emp.api.domain.req.SetTagRequest;
import com.tca.emp.api.domain.req.CreateEmpRequest;
import com.tca.emp.api.domain.req.SetStatusRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
public class EmpAccActionService extends AbstractEmpService<Object, Object> {
    @Override
    protected void validateParameter(Object reqParameter) throws Exception {
        if(ObjectUtils.isEmpty(reqParameter)){ ex(GlobalRequestEnum.PARAM_LOSS); }
    }

    @Override
    public Response<Object> process(Object reqParameter) throws Exception {
        return null;
    }

    public Response<Void> createEmployee(CreateEmpRequest createEmpRequest) throws Exception {
        validateParameter(createEmpRequest);
        if(!validatePermission()) { return Response.genResp(GlobalSystemEnum.PERMISSION_ERROR); }

        Boolean checkAvailability = ObjectUtils.isEmpty(authService.findByUsername(createEmpRequest.getUsername())) ? Boolean.TRUE : Boolean.FALSE;
        if(checkAvailability){
            CreateEmpPo createEmpPo = CreateEmpPo.init(createEmpRequest);
            empAccMapper.createEmployee(createEmpPo);
        }
        else {
            return Response.genFailResp();
        }

        return Response.genSuccessResp();
    }

    public Response<Void> setTag(SetTagRequest setTagRequest) throws Exception{
        validateParameter(setTagRequest);
        empAccMapper.setTag(setTagRequest.getEmployeeId(), setTagRequest.getTagId(), new Date());
        return Response.genSuccessResp();
    }

    public Response<Void> setStatus(SetStatusRequest setStatusRequest) throws Exception{
        validateParameter(setStatusRequest);
        empAccMapper.setStatus(setStatusRequest.getEmployeeId(), setStatusRequest.getStatus(), new Date());
        return Response.genSuccessResp();
    }
}
