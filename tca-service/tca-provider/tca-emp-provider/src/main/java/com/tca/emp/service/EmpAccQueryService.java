package com.tca.emp.service;

import com.tca.core.Response;
import com.tca.core.constant.enums.GlobalRequestEnum;
import com.tca.core.constant.enums.GlobalSystemEnum;
import com.tca.emp.abstracts.AbstractEmpService;
import com.tca.emp.api.vo.EmployeeDetail;
import com.tca.emp.api.vo.FullEmployeeDetail;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author star.lee
 */
@Service
public class EmpAccQueryService extends AbstractEmpService<Object, Object> {
    @Override
    protected void validateParameter(Object reqParameter) throws Exception {
        if(ObjectUtils.isEmpty(reqParameter)) { ex(GlobalRequestEnum.PARAM_LOSS); }
    }

    @Override
    public Response<Object> process(Object reqParameter) throws Exception {
        return null;
    }

    public Response<EmployeeDetail> queryUsername(String username) throws Exception{
        validateParameter(username);
        return Response.genResp(GlobalSystemEnum.OK, empAccMapper.findEmpByUsername(username));
    }

    public Response<List<FullEmployeeDetail>> queryEmployees(){
        return Response.genResp(GlobalSystemEnum.OK, empAccMapper.findAll());
    }
}
