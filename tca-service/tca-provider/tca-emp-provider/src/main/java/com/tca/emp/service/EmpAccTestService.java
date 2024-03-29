package com.tca.emp.service;

import com.tca.core.Response;
import com.tca.emp.abstracts.AbstractEmpService;
import org.springframework.stereotype.Service;

/**
 * @author star.lee
 */
@Service
public class EmpAccTestService extends AbstractEmpService<Void, Object> {
    @Override
    protected void validateParameter(Void reqParameter) throws Exception {

    }

    @Override
    public Response<Object> process(Void reqParameter) throws Exception {

        return Response.genResp(String.format("Id: %s", getEmployeeId()));
    }
}
