package com.tca.emp.abstracts;

import com.tca.auth.api.feign.AuthService;
import com.tca.core.entity.EmpAcc;
import com.tca.emp.api.constant.TagType;
import com.tca.emp.api.domain.vo.EmployeeDetail;
import com.tca.emp.mapper.EmpAccMapper;
import com.tca.utils.DynamicDataSource;
import com.tca.utils.Response;
import com.tca.utils.constant.abstracts.AbstractWebService;
import com.tca.utils.constant.finals.EnumManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

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

    public Boolean validatePermission(){
        switch (Objects.requireNonNull(EnumManager.getIndexEnumByIndex(getEmployee().getTagId().getIndex(), TagType.class))){
            case CEO:
            case HR:
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    protected final EmployeeDetail getEmployee(){
//        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authenticationToken.getPrincipal();
//        return empAccMapper.findEmpByUsername(userDetails.getUsername());
        EmpAcc empAcc = authService.getEmployeeInfo();
        EmployeeDetail employeeDetail = new EmployeeDetail();
        BeanUtils.copyProperties(empAcc, employeeDetail);
        return employeeDetail;
    }
    protected final Integer getEmployeeId(){
        return getEmployee().getId();
    }
}
