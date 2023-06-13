package com.experimental.tca.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.experimental.tca.model.AuditLog;


@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Timestamp>{

}
