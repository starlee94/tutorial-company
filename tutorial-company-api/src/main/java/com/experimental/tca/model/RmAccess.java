package com.experimental.tca.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rm_access")
public class RmAccess {
	
	@Id
	private Integer i_rm_access_id;
	private String vc_rm_name;
	private Integer i_entry_count_daily;
}
