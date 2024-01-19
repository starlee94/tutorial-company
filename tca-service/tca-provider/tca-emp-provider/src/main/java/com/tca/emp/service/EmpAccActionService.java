package com.tca.emp.service;

import com.tca.core.Response;
import com.tca.core.constant.enums.GlobalRequestEnum;
import com.tca.emp.abstracts.AbstractEmpService;
import com.tca.emp.api.domain.po.CreateEmpPo;
import com.tca.emp.api.domain.req.CreateEmpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
        Boolean checkAvailability = ObjectUtils.isEmpty(authService.findByUsername(createEmpRequest.getUsername())) ? Boolean.TRUE : Boolean.FALSE;
        if(checkAvailability){
            CreateEmpPo createEmpPo = CreateEmpPo.init(createEmpRequest);
            empAccMapper.createEmployee(createEmpPo);
        }

        return Response.genSuccessResp();
    }
}
