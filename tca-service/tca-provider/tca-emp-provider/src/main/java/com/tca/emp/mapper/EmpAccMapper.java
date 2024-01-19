package com.tca.emp.mapper;

import com.tca.emp.api.domain.po.CreateEmpPo;
import com.tca.emp.api.domain.vo.EmployeeDetail;
import com.tca.emp.api.domain.vo.FullEmployeeDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author star.lee
 */
@Mapper
public interface EmpAccMapper {
    EmployeeDetail findEmpByUsername(String username);

    List<FullEmployeeDetail> findAll();

    Void createEmployee(CreateEmpPo createEmpPo);
}
