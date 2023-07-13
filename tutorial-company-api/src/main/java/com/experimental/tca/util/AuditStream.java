package com.experimental.tca.util;

import com.experimental.tca.entity.AuditLog;
import com.experimental.tca.mapper.AuditLogMapper;

import java.sql.Timestamp;

/**
 * @author star.lee
 */
public enum AuditStream {
    ;

    public static AuditLog audit(Timestamp time, String description, Integer id, AuditLogMapper auditLogMapper){
        AuditLog auditLog = new AuditLog();
        auditLog.setTimeStamp(time);
        auditLog.setDescription(description);
        auditLog.setEmployeeId(id);

        auditLogMapper.save(auditLog);

        return auditLog;
    }
}
