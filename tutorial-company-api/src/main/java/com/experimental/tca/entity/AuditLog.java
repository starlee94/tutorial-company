package com.experimental.tca.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "audit_log")
public class AuditLog{
	
	@Id
	private Timestamp dt_timestamp;
	private String vc_audit_descript;
	private Integer i_emp_id;
}
