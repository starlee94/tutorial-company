package com.tca.emp.service;

import com.tca.core.Response;
import com.tca.emp.abstracts.AbstractEmpService;
import org.springframework.stereotype.Service;

@Service
public class EmpAccService extends AbstractEmpService<Object, Object> {
    @Override
    protected void validateParameter(Object reqParameter) throws Exception { }

    @Override
    public Response<Object> process(Object reqParameter) throws Exception {
        return null;
    }

}
