package com.experimental.tca.mapper;

import com.experimental.tca.entity.v1.AuditLog;
import com.experimental.tca.entity.v1.EmpAcc;
import org.apache.ibatis.annotations.*;


/**
 * @author star.lee
 */
@Mapper
public interface AuditLogMapper {

    @Insert("insert into audit_log(dt_timestamp,vc_audit_descript,i_emp_id) values(#{timeStamp},#{description},#{employeeId})")
    void save(AuditLog auditLog);

    @Result(property = "timeStamp", column = "dt_timestamp")
    @Result(property = "description", column = "vc_audit_descript")
    @Result(property = "employeeId", column = "i_emp_id")
    @Select("select * from audit_log where i_emp_id=#{id}")
    EmpAcc selectById(Integer id);

}
