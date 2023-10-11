package com.tca.auth.abstracts;

import com.tca.utils.DynamicDataSource;
import com.tca.utils.Response;
import com.tca.utils.abstracts.AbstractWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

public abstract class AbstractAuthService<E extends Object, T extends Object> extends AbstractWebService<E, T> {

    @Override
    public Response<T> handle(E reqParameter) throws Exception {
        try {
            return super.handle(reqParameter);
        } finally {
            DynamicDataSource.clearDataSource();
        }
    }
}
