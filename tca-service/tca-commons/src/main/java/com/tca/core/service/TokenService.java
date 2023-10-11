package com.tca.core.service;

import com.tca.core.mapper.TokenMapper;
import com.tca.utils.Response;
import com.tca.utils.abstracts.AbstractTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService extends AbstractTokenService<Object, Object> {


    public Optional<String> verifyToken (String token) { return tokenMapper.verifyToken(token); }

    public  int clearToken (String token) { return tokenMapper.clearToken(token); }

    @Override
    protected void validateParameter(Object reqParameter) throws Exception {

    }

    @Override
    public Response<Object> process(Object reqParameter) throws Exception {
        return null;
    }
}
