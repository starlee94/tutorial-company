package com.experimental.tca.entity.v1;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author star.lee
 */
@Data
@Entity
@Table(name = "audit_log")
public class AuditLog{
	
	@Id
	@Column(name="dt_timestamp")
	private Timestamp timeStamp;

	@Column(name="vc_audit_descript")
	private String description;

	@Column(name="i_emp_id")
	private Integer employeeId;
}
