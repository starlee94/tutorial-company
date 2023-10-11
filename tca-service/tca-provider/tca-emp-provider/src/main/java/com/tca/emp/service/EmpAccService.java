package com.tca.emp.service;

import com.tca.emp.abstracts.AbstractEmpService;
import com.tca.emp.api.vo.EmployeeDetail;
import com.tca.utils.Response;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpAccService extends AbstractEmpService<Object, Object> {
    @Override
    protected void validateParameter(Object reqParameter) throws Exception { }

    @Override
    public Response<Object> process(Object reqParameter) throws Exception {
        return null;
    }

}
