package com.tca.utils.abstracts;

import com.tca.core.mapper.TokenMapper;
import com.tca.utils.DynamicDataSource;
import com.tca.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractTokenService<E extends Object, T extends Object> extends AbstractWebService<E, T> {

    @Autowired
    protected TokenMapper tokenMapper;
    @Override
    public Response<T> handle(E reqParameter) throws Exception {
        try {
            return super.handle(reqParameter);
        } finally {
            DynamicDataSource.clearDataSource();
        }
    }
}
