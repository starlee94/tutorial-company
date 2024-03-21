package com.tca.emp.mapper;

import com.tca.emp.api.constant.EmployeeStatus;
import com.tca.emp.api.constant.TagType;
import com.tca.emp.api.domain.po.CreateEmpPo;
import com.tca.emp.api.domain.vo.EmployeeDetail;
import com.tca.emp.api.domain.vo.FullEmployeeDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author star.lee
 */
@Mapper
public interface EmpAccMapper {

    EmployeeDetail findById(@Param("empId") Integer empId);
    EmployeeDetail findEmpByUsername(@Param("username") String username);

    List<FullEmployeeDetail> findAll();

    void createEmployee(CreateEmpPo createEmpPo);

    void setTag(@Param("empId") Integer empId, @Param("tagId") TagType tagId, @Param("updateTime")Date updateTime);

    void setStatus(@Param("empId") Integer empId, @Param("status") EmployeeStatus status, @Param("updateTime")Date updateTime);

    void setSecret(@Param("username") String username, @Param("secret") String secret);

    Integer verifySecret(@Param("secret") String secret);
}
