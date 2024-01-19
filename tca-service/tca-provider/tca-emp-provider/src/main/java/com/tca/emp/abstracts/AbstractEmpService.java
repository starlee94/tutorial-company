package com.tca.emp.abstracts;

import com.tca.auth.api.feign.AuthService;
import com.tca.core.DynamicDataSource;
import com.tca.core.Response;
import com.tca.core.constant.abstracts.AbstractWebService;
import com.tca.emp.api.domain.vo.EmployeeDetail;
import com.tca.emp.mapper.EmpAccMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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

    protected final EmployeeDetail getEmployee(){
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authenticationToken.getPrincipal();
        return empAccMapper.findEmpByUsername(userDetails.getUsername());
    }
    protected final Integer getEmployeeId(){
        return getEmployee().getId();
    }
}
