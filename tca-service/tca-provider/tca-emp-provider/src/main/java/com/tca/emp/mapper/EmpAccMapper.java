package com.tca.emp.mapper;

import com.tca.emp.api.vo.EmployeeDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author star.lee
 */
@Mapper
public interface EmpAccMapper {
    EmployeeDetail findEmpByUsername(String username);
}
