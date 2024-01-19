package com.tca.emp.mapper;

import com.tca.emp.api.constant.EmployeeStatus;
import com.tca.emp.api.constant.TagType;
import com.tca.emp.api.domain.po.CreateEmpPo;
import com.tca.emp.api.domain.vo.EmployeeDetail;
import com.tca.emp.api.domain.vo.FullEmployeeDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author star.lee
 */
@Mapper
public interface EmpAccMapper {

    EmployeeDetail findById(@Param("empId") Integer empId);
    EmployeeDetail findEmpByUsername(@Param("username") String username);

    List<FullEmployeeDetail> findAll();

    Void createEmployee(CreateEmpPo createEmpPo);

    Void setTag(@Param("empId") Integer empId, @Param("tagId") TagType tagId);

    Void setStatus(@Param("empId") Integer empId, @Param("status") EmployeeStatus status);
}
