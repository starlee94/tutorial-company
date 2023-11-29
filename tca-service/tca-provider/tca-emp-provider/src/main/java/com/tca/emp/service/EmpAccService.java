package com.tca.emp.service;

import com.tca.core.Response;
import com.tca.core.constant.enums.GlobalSystemEnum;
import com.tca.emp.abstracts.AbstractEmpService;
import org.springframework.stereotype.Service;

/**
 * @author star.lee
 */
@Service
public class EmpAccService extends AbstractEmpService<Object, Object> {
    @Override
    protected void validateParameter(Object reqParameter) throws Exception { }

    @Override
    public Response<Object> process(Object reqParameter) throws Exception {
        return null;
    }

    public Response<Void> test() throws Exception{ return Response.genResp(GlobalSystemEnum.OK); }

}
