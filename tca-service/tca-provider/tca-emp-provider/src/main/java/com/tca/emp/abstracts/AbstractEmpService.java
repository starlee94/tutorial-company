package com.tca.emp.abstracts;

import com.tca.auth.api.feign.AuthService;
import com.tca.emp.mapper.EmpAccMapper;
import com.tca.core.DynamicDataSource;
import com.tca.core.Response;
import com.tca.core.constant.abstracts.AbstractWebService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author star.lee
 */
public abstract class AbstractEmpService<E extends Object, T extends Object> extends AbstractWebService<E, T> {

    //------------Mappers------------
    @Autowired
    protected EmpAccMapper empAccMapper;

    //------------Services------------

    @Autowired
    protected AuthService authService;

    @Override
    public Response<T> handle(E reqParameter) throws Exception {
        try {
            return super.handle(reqParameter);
        } finally {
            DynamicDataSource.clearDataSource();
        }
    }

    protected final Integer getEmployeeId(){
        return authService.getEmployeeInfo().getId();
    }
}
