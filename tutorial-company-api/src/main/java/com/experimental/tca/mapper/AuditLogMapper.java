package com.experimental.tca.mapper;

import com.experimental.tca.model.AuditLog;
import com.experimental.tca.model.EmpAcc;
import org.apache.ibatis.annotations.*;


@Mapper
public interface AuditLogMapper {

    @Insert("insert into audit_log(dt_timestamp,vc_audit_descript,i_emp_id) values(#{dt_timestamp},#{vc_audit_descript},#{i_emp_id})")
    void save(AuditLog auditLog);

    @Result(property = "dt_timestamp", column = "dt_timestamp")
    @Result(property = "vc_audit_descript", column = "vc_audit_descript")
    @Result(property = "i_emp_id", column = "i_emp_id")
    @Select("select * from audit_log where i_emp_id=#{i_emp_id}")
    EmpAcc selectById(Integer id);

}
