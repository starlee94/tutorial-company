package com.tca.emp.mapper;

import com.tca.emp.api.vo.EmployeeDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface EmpAccMapper {

    EmployeeDetail findEmpByUsername(String username);

    Optional<String> verifyToken (String token);

    int clearToken (String token);

}
