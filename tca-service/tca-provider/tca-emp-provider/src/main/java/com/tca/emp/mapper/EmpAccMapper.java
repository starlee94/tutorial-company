package com.tca.emp.mapper;

import com.tca.emp.api.vo.EmployeeDetail;
import com.tca.emp.api.vo.FullEmployeeDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author star.lee
 */
@Mapper
public interface EmpAccMapper {
    EmployeeDetail findEmpByUsername(String username);

    List<FullEmployeeDetail> findAll();
}
