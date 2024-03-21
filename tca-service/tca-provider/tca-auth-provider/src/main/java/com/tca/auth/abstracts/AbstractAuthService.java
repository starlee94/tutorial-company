package com.tca.auth.abstracts;

import com.tca.auth.mapper.AuthMapper;
import com.tca.core.service.JwtService;
import com.tca.utils.DynamicDataSource;
import com.tca.utils.Response;
import com.tca.utils.constant.abstracts.AbstractWebService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author star.lee
 */
public abstract class AbstractAuthService<E extends Object, T extends Object> extends AbstractWebService<E, T> {

    //------------Mappers------------
    @Autowired
    protected AuthMapper authMapper;

    //------------Services------------
    @Autowired
    protected JwtService jwtService;

    protected String respMsg;

    @Override
    public Response<T> handle(E reqParameter) throws Exception {
        try {
            return super.handle(reqParameter);
        } finally {
            DynamicDataSource.clearDataSource();
        }
    }
}
